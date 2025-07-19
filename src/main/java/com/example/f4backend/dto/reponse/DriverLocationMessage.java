package com.example.f4backend.dto.reponse;

import lombok.Data;

@Data
public class DriverLocationMessage {
    private String driverId;
    private double latitude;
    private double longitude;
}
