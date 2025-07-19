package com.example.f4backend.controller;


import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.BookingAcceptNotification;
import com.example.f4backend.dto.reponse.BookingResponse;
import com.example.f4backend.dto.reponse.UserBookingResponse;
import com.example.f4backend.dto.request.BookingAction;
import com.example.f4backend.dto.request.BookingRequest;
import com.example.f4backend.dto.request.RatingRequest;
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
        switch (request.getVehicleTypeId()){
            case 1:
                request.setPrice(request.getDistance() * 7000);
                break;
            case 2:
                request.setPrice(request.getDistance() * 12000);
                break;
            case 3:
                request.setPrice(request.getDistance() * 16000);
                break;
            default:

        }
        String nearestDriverId = nearbyDrivers.get(0);
        messagingTemplate.convertAndSend("/topic/drivers" + nearestDriverId, request);
        UserBookingResponse userBookingResponse = new UserBookingResponse();
        userBookingResponse.setBookingId(bookingId);
        userBookingResponse.setMessage("Đã gửi yêu cầu đặt xe, vui lòng chờ");
        return ApiResponse.<UserBookingResponse>builder()
                .code(1000)
                .result(userBookingResponse)
                .build();
    }
    @MessageMapping("/accept")
    public void acceptBooking(BookingAction bookingAction){
        boolean accepted = bookingService.acceptBooking(bookingAction.getBookingId(), bookingAction.getDriverId());
        if (accepted) {
            Booking booking = bookingRepository.findById(bookingAction.getBookingId()).orElseThrow();
            messagingTemplate.convertAndSendToUser(
                    booking.getUser().getId(),
                    "/queue/booking-status",
                    new BookingAcceptNotification(
                            "BOOKING_ACCEPTED",
                            booking.getDriver().getFullName(),
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
                bookingAction.getDriverId(),
                bookingAction.getPrice()
        );

        if (!completed) {
            return ApiResponse.<String>builder()
                    .message("Không thể hoàn tất đơn hàng")
                    .build();
        }

        String statusKey = "driver_status:" + bookingAction.getDriverId();
        redisTemplate.opsForHash().put(statusKey, "status", "AVAILABLE");
        redisTemplate.expire(statusKey, Duration.ofSeconds(60));

        Booking booking = bookingRepository.findById(bookingAction.getBookingId())
                .orElseThrow();

        messagingTemplate.convertAndSendToUser(
                booking.getUser().getId(),
                "/queue/booking-completed",
                new BookingAcceptNotification(
                        "BOOKING_COMPLETED",
                        booking.getDriver().getFullName(),
                        "Tài xế đã hoàn thành chuyến đi"
                )
        );
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

    @GetMapping("/user/{userId}")
    public ApiResponse<List<BookingResponse>> getBookingsByUser(@PathVariable String userId) {
        return ApiResponse.<List<BookingResponse>>builder()
                .code(1000)
                .result(bookingService.getBookingsByUserId(userId))
                .build();
    }
    @GetMapping("/driver/{driverId}")
    public ApiResponse<List<BookingResponse>> getBookingsByDriver(@PathVariable String driverId) {
        return ApiResponse.<List<BookingResponse>>builder()
                .code(1000)
                .result(bookingService.getBookingsByDriverId(driverId))
                .build();
    }

    @GetMapping("/cancel/{bookingId}")
    public ApiResponse<String> cancelBooking(@PathVariable String bookingId){
        bookingService.cancelBooking(bookingId);
        return ApiResponse.<String>builder()
                .message("Đã hủy đơn")
                .build();
    }

    @GetMapping("/{bookingId}/is-rejected")
    public ApiResponse<Boolean> isBookingRejected(@PathVariable String bookingId) {
        boolean isRejected = bookingService.isBookingRejected(bookingId);
        return ApiResponse.<Boolean>builder()
                .result(isRejected)
                .build();
    }

    @GetMapping("/{bookingId}")
    public ApiResponse<BookingResponse> getBookingById(@PathVariable String bookingId){
        return ApiResponse.<BookingResponse>builder()
                .result(bookingService.getBookingById(bookingId))
                .build();
    }

    @PostMapping("/rating")
    public ApiResponse<String> ratingForDriver(@RequestBody RatingRequest ratingRequest){
        bookingService.ratingForDriver(ratingRequest);
        return ApiResponse.<String>builder()
                .code(1000)
                .result("Danh gia thanh cong")
                .build();
    }
}