package com.example.f4backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}