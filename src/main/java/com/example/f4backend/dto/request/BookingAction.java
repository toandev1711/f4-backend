package com.example.f4backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingAction {
    private String bookingId;
    private String driverId;
}