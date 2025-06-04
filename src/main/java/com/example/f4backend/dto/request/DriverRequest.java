package com.example.f4backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Data
public class DriverRequest {
    @NotNull(message = "driverTypeId_NOT_null")
    private Integer driverTypeId;
    private Boolean gender;
    private String password;
    private String fullName;
    private String portrait;
    private Date dateBirth;
    private String address;
    private String email;
    private String phone;
    private Boolean isLocked;
}
