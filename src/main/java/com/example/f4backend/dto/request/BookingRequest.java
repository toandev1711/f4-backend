package com.example.f4backend.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private String userId;
    private String orderType;
    private Integer vehicleTypeId;
    private String pickupAddress;
    private String dropOffAddress;
    private String discountCode;
    private String descriptionNotes;
    private String bookingId;
    private BigDecimal pickupLatitude;
    private BigDecimal pickupLongitude;
    private BigDecimal dropoffLatitude;
    private BigDecimal dropoffLongitude;
    private String fullName;
    private Double price;
    private Double distance;
}