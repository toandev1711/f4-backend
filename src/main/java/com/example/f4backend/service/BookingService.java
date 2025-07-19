package com.example.f4backend.service;
import com.example.f4backend.dto.reponse.BookingResponse;
import com.example.f4backend.dto.request.BookingRequest;
import com.example.f4backend.dto.request.RatingRequest;
import com.example.f4backend.entity.*;
import com.example.f4backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookingService {
    private final UserRepository userRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final BookingRepository bookingRepository;
    private final BookingStatusRepository bookingStatusRepository;
    private final DriverRepository driverRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final DriverRevenueService driverRevenueService;
    private final RedisTemplate<String, String> redisTemplate;
    private final static String GEO_KEY = "driver_locations";
    public String createBooking(BookingRequest dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        VehicleType vehicleType = vehicleTypeRepository.findById(dto.getVehicleTypeId()).orElseThrow();
        Booking booking = new Booking();
        booking.setCreatedDate(LocalDateTime.now());
        booking.setUser(user);
        booking.setOrderType(dto.getOrderType());
        BookingDetail detail = new BookingDetail();
        detail.setBooking(booking);
        detail.setPickupAddress(dto.getPickupAddress());
        detail.setDropoffAddress(dto.getDropOffAddress());
        detail.setVehicleType(vehicleType);
        detail.setDiscountCode(dto.getDiscountCode());
        detail.setDescriptionNotes(dto.getDescriptionNotes());
        detail.setPrice(dto.getPrice());
        switch (dto.getVehicleTypeId()){
            case 1:
                detail.setPrice(dto.getDistance() * 7000);
                break;
            case 2:
                detail.setPrice(dto.getDistance() * 12000);
                break;
            case 3:
                detail.setPrice(dto.getDistance() * 16000);
                break;
            default:

        }
        booking.setBookingDetail(detail);
        // find and add status "PENDING"
        BookingStatus status = bookingStatusRepository
                .findByStatusNameIgnoreCase("PENDING")
                .orElseGet(() -> {
                    BookingStatus newStatus = new BookingStatus();
                    newStatus.setStatusName("PENDING");
                    return bookingStatusRepository.save(newStatus);
                });
        Coordinates coordinates = Coordinates.builder()
                .bookingDetail(detail)
                .dropoffLatitude(dto.getDropoffLatitude())
                .dropoffLongitude(dto.getDropoffLongitude())
                .pickupLatitude(dto.getPickupLatitude())
                .pickupLongitude(dto.getPickupLongitude())
                .build();
        booking.setBookingStatus(status);
        Booking saved = bookingRepository.save(booking);
        coordinatesRepository.save(coordinates);
        return saved.getBookingId();
    }

    public boolean acceptBooking(String bookingId, String driverId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) return false;
        Booking booking = optionalBooking.get();
        BookingStatus currentStatus = booking.getBookingStatus();
        if (currentStatus == null || !"PENDING".equalsIgnoreCase(currentStatus.getStatusName())) {
            return false;
        }
        BookingStatus acceptedStatus = bookingStatusRepository.findByStatusNameIgnoreCase("ACCEPTED")
                .orElseGet(() -> {
                    BookingStatus newStatus = new BookingStatus();
                    newStatus.setStatusName("ACCEPTED");
                    return bookingStatusRepository.save(newStatus);
                });

        Optional<Driver> optionalDriver = driverRepository.findById(driverId);
        if (optionalDriver.isEmpty()) return false;

        booking.setDriver(optionalDriver.get());
        booking.setBookingStatus(acceptedStatus);
        bookingRepository.save(booking);
        return true;
    }

    public boolean completeBooking(String bookingId, String driverId, BigDecimal price) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) return false;
        Booking booking = optionalBooking.get();
        BookingStatus status = bookingStatusRepository
                .findByStatusNameIgnoreCase("COMPLETED")
                .orElseGet(() -> {
                    BookingStatus newStatus = new BookingStatus();
                    newStatus.setStatusName("COMPLETED");
                    return bookingStatusRepository.save(newStatus);
                });

        if (!booking.getDriver().getDriverId().equals(driverId)) return false;

        booking.setBookingStatus(status);
        bookingRepository.save(booking);

        driverRevenueService.addRevenueForBooking(
                driverId,
                bookingId,
                LocalDate.now(),
                price
        );


        return true;
    }

    public List<BookingResponse> getBookingsByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        List<Booking> listBooking = bookingRepository.findByUser(user);
        List<BookingResponse> responses = new ArrayList<>();
        for(Booking b : listBooking){
            BookingResponse bookingResponse = BookingResponse.builder()
                    .driver(Objects.requireNonNullElse(b.getDriver(), new Driver()).getFullName())
                    .user(Objects.requireNonNullElse(b.getUser(), new User()).getFullName())
                    .dropoffAddress(Objects.requireNonNullElse(b.getBookingDetail(), new BookingDetail()).getDropoffAddress())
                    .pickupAddress(Objects.requireNonNullElse(b.getBookingDetail(), new BookingDetail()).getPickupAddress())
                    .createdDate(b.getCreatedDate())
                    .rating(b.getRating())
                    .price(b.getBookingDetail().getPrice())
                    .build();

            responses.add(bookingResponse);
        }
        return responses;
    }
    public List<BookingResponse> getBookingsByDriverId(String driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found: " + driverId));
        List<Booking> listBooking = bookingRepository.findByDriver(driver);
        List<BookingResponse> responses= new ArrayList<>();
        for(Booking b : listBooking){
            BookingResponse bookingResponse = BookingResponse.builder()
//                   .driver(Objects.requireNonNullElse(b.getDriver(), new Driver()).getFullName())
                    .user(Objects.requireNonNullElse(b.getUser(), new User()).getFullName())
                    .dropoffAddress(Objects.requireNonNullElse(b.getBookingDetail(), new BookingDetail()).getDropoffAddress())
                    .pickupAddress(Objects.requireNonNullElse(b.getBookingDetail(), new BookingDetail()).getPickupAddress())
                    .createdDate(b.getCreatedDate())
                    .price(b.getBookingDetail().getPrice())
                    .rating(b.getRating())
                    .build();
            responses.add(bookingResponse);
        }
        return responses;
    }

    public void cancelBooking(String bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        BookingStatus rejectStatus = bookingStatusRepository.findByStatusNameIgnoreCase("REJECT")
                .orElseGet(() -> {
                    BookingStatus newStatus = new BookingStatus();
                    newStatus.setStatusName("REJECT");
                    return bookingStatusRepository.save(newStatus);
                });
        booking.setBookingStatus(rejectStatus);
        bookingRepository.save(booking);
    }

    public boolean isBookingRejected(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy booking"));
        String status = booking.getBookingStatus().getStatusName();
        return "REJECT".equalsIgnoreCase(status);
    }

    public BookingResponse getBookingById(String bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy booking"));

        return BookingResponse.builder()
                .driver(booking.getDriver().getFullName())
                .pickupAddress(booking.getBookingDetail().getPickupAddress())
                .dropoffAddress(booking.getBookingDetail().getDropoffAddress())
                .price(booking.getBookingDetail().getPrice())
                .build();
    }

    public void ratingForDriver(RatingRequest ratingRequest){
        Booking booking = bookingRepository.findById(ratingRequest.getBookingId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy booking"));

        booking.setRating(ratingRequest.getRating());
        booking.setRatingNote(ratingRequest.getRatingNote());
        bookingRepository.save(booking);
    }
}