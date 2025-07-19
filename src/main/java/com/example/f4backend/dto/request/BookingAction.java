package com.example.f4backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BookingAction {
    private String bookingId;
    private String driverId;
    private BigDecimal price;
}