package com.example.f4backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DriverRequest {
    @NotBlank(message = "USERID_NOT_BLANK")
    private String userId;
    private String driverType;
    private String driverStatus;
    private Double priceMoney;
}
