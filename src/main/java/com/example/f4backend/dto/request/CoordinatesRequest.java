package com.example.f4backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CoordinatesRequest {
    @NotNull
    private String deliveryDetailId;
    private BigDecimal pickupLatitude;
    private BigDecimal pickupLongitude;
    private BigDecimal dropoffLatitude;
    private BigDecimal dropoffLongitude;
}