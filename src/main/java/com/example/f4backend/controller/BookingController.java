package com.example.f4backend.controller;


import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.BookingAcceptNotification;
import com.example.f4backend.dto.reponse.UserBookingResponse;
import com.example.f4backend.dto.request.BookingAction;
import com.example.f4backend.dto.request.BookingRequest;
import com.example.f4backend.entity.Booking;
import com.example.f4backend.entity.Message;
import com.example.f4backend.repository.BookingRepository;
import com.example.f4backend.service.BookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.domain.geo.GeoReference;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import org.springframework.data.geo.Point;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Circle;

import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.GeoOperations;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private final BookingService bookingService;
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private final static String GEO_KEY = "driver_locations";
    @PostMapping("/create")
    public ApiResponse<UserBookingResponse> createBooking(@RequestBody BookingRequest request) throws JsonProcessingException {
        String bookingId = bookingService.createBooking(request);
        request.setBookingId(bookingId);
        List<String> nearbyDrivers = findNearbyDriversFiltered(
                request.getPickupLatitude(),
                request.getPickupLongitude(),
                10000,
                10,
                request.getVehicleTypeId()
        );
        if(nearbyDrivers.isEmpty())
            return ApiResponse.<UserBookingResponse>builder()
                    .code(500)
                    .message("No driver available")
                    .build();
        request.setPrice(200.0);
        String nearestDriverId = nearbyDrivers.get(0);
        messagingTemplate.convertAndSend("/topic/drivers" + nearestDriverId, request);
        UserBookingResponse userBookingResponse = new UserBookingResponse();
        userBookingResponse.setMessage("Booking was send to driver");
        return ApiResponse.<UserBookingResponse>builder()
                .code(1000)
                .result(userBookingResponse)
                .build();
    }
    @MessageMapping("/accept")
    public void acceptBooking(BookingAction bookingAction){
        boolean accepted = bookingService.acceptBooking(bookingAction.getBookingId(), bookingAction.getDriverId());
        if (accepted) {
            Optional<Booking> optionalBooking = bookingRepository.findById(bookingAction.getBookingId());
            Booking booking = optionalBooking.get();
            messagingTemplate.convertAndSendToUser(
                    booking.getUser().getId(),
                    "/queue/booking-status",
                    new BookingAcceptNotification(
                            "BOOKING_ACCEPTED",
                            booking.getDriver().getDriverId(),
                            "Tài xế đã chấp nhận yêu cầu"
                    )
            );

            String statusKey = "driver_status:" + booking.getDriver().getDriverId();
            redisTemplate.opsForHash().put(statusKey, "status", "BUSY");
            redisTemplate.expire(statusKey, Duration.ofSeconds(60));
        }
    }

    @PostMapping("/complete")
    public ApiResponse<String> completeBooking(@RequestBody BookingAction bookingAction) {
        boolean completed = bookingService.completeBooking(
                bookingAction.getBookingId(),
                bookingAction.getDriverId()
        );

        if (!completed) {
            return ApiResponse.<String>builder()
                    .message("Không thể hoàn tất đơn hàng")
                    .build();
        }

        String statusKey = "driver_status:" + bookingAction.getDriverId();
        redisTemplate.opsForHash().put(statusKey, "status", "AVAILABLE");
        redisTemplate.expire(statusKey, Duration.ofSeconds(60));

        return ApiResponse.<String>builder()
                .message("Hoàn tất đơn hàng")
                .build();
    }
    public List<String> findNearbyDriversFiltered(BigDecimal lat, BigDecimal lon, double radiusMeters, int limit, Integer vehicleTypeId) {
        GeoOperations<String, String> geoOps = (GeoOperations<String, String>) redisTemplate.opsForGeo();

        GeoReference<String> reference = GeoReference.fromCoordinate(new Point(lon.doubleValue(), lat.doubleValue()));
        RedisGeoCommands.GeoSearchCommandArgs args = RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs()
                .includeDistance()
                .sortAscending()
                .limit(limit);

        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo()
                .search("driver_locations", reference, new Distance(radiusMeters / 1000.0, Metrics.KILOMETERS), args);
        if (results == null) return Collections.emptyList();
        List<String> filteredDrivers = new ArrayList<>();
        for (GeoResult<GeoLocation<String>> geoResult : results.getContent()) {
            String driverId = geoResult.getContent().getName();
            String statusKey = "driver_status:" + driverId;
            Map<Object, Object> driverInfo = redisTemplate.opsForHash().entries(statusKey);
            if (driverInfo == null || driverInfo.isEmpty()) continue;
            String status = (String) driverInfo.get("status");
            String driverVehicleTypeIdStr = (String) driverInfo.get("vehicleTypeId");
            if (!"AVAILABLE".equals(status)) continue;
            if (driverVehicleTypeIdStr == null) continue;
            Integer driverVehicleTypeId = Integer.parseInt(driverVehicleTypeIdStr);
            if (!driverVehicleTypeId.equals(vehicleTypeId)) continue;
            filteredDrivers.add(driverId);
            if (filteredDrivers.size() >= limit) break;
        }
        return filteredDrivers;
    }
}