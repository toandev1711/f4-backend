package com.example.f4backend.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CoordinatesResponse {
    private String coordinateId;

    private String deliveryDetailId;
    private BigDecimal pickupLatitude;
    private BigDecimal pickupLongitude;
    private BigDecimal dropoffLatitude;
    private BigDecimal dropoffLongitude;
}