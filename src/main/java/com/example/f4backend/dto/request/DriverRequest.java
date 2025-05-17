package com.example.f4backend.dto.request;

import lombok.Data;

@Data
public class DriverRequest {
    private String userId;
    private String driverType;
    private String driverStatus;
    private Double priceMoney;
}
