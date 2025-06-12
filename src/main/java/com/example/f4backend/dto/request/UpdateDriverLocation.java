package com.example.f4backend.dto.request;


import lombok.Data;

@Data
public class UpdateDriverLocation {
    private String driverId;
    private double latitude;
    private double longitude;
}
