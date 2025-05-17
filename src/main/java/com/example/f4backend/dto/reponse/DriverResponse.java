package com.example.f4backend.dto.reponse;

import com.example.f4backend.enums.DriverStatus;
import com.example.f4backend.enums.DriverType;
import lombok.Data;

@Data
public class DriverResponse {
    private String driverId;
    private String userId;
    private DriverType driverType;
    private DriverStatus driverStatus;
    private Double priceMoney;
}
