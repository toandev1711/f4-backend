package com.example.f4backend.service;

import com.example.f4backend.dto.request.BookingRequest;
import com.example.f4backend.dto.request.BookingRequestForDriver;
import com.example.f4backend.entity.*;
import com.example.f4backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final UserRepository userRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final BookingRepository bookingRepository;
    private final BookingStatusRepository bookingStatusRepository;
    private final DriverRepository driverRepository;
    public String createBooking(BookingRequest dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        VehicleType vehicleType = vehicleTypeRepository.findById(dto.getVehicleTypeId()).orElseThrow();
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setOrderType(dto.getOrderType());
        BookingDetail detail = new BookingDetail();
        detail.setBooking(booking);
        detail.setPickupAddress(dto.getPickupAddress());
        detail.setDropoffAddress(dto.getDropOffAddress());
        detail.setVehicleType(vehicleType);
        detail.setDiscountCode(dto.getDiscountCode());
        detail.setDescriptionNotes(dto.getDescriptionNotes());
        booking.setBookingDetail(detail);

        // find and add status "PENDING"
        BookingStatus status = bookingStatusRepository
                .findByStatusNameIgnoreCase("PENDING")
                .orElseGet(() -> {
                    BookingStatus newStatus = new BookingStatus();
                    newStatus.setStatusName("PENDING");
                    return bookingStatusRepository.save(newStatus);
                });
        booking.setBookingStatus(status);
        Booking saved = bookingRepository.save(booking);
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

}