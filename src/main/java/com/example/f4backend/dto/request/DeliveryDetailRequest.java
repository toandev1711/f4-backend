package com.example.f4backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DeliveryDetailRequest {
    private String orderId;
    private String pickupAddress;
    private String dropoffAddress;
    private Integer vehicleTypeId;
    private BigDecimal price;
    private String packageType;
    private BigDecimal weight;
    private String size;
    private String image;
    private String discountCode;
    private String descriptionNotes;
}