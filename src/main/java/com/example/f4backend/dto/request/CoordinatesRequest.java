package com.example.f4backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CoordinatesRequest {
    private BigDecimal pickupLatitude;
    private BigDecimal pickupLongitude;
    private BigDecimal dropoffLatitude;
    private BigDecimal dropoffLongitude;
}