package com.example.f4backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.WithBy;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestForDriver {
    private String userId;
    private String orderType;
    private Integer vehicleTypeId;
    private String pickupAddress;
    private String dropOffAddress;
    private String discountCode;
    private String descriptionNotes;
}
