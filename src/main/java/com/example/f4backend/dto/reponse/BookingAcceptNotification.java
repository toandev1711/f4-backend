package com.example.f4backend.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingAcceptNotification {
    private String type;
    private String driverId;
    private String message;
}