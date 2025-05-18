package com.example.f4backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;

@Data
public class LicenseCarRequest {
    @NotBlank(message = "LICENSE_NUMBER_NOT_BLANK")
    private String licenseNumber;
    private String licenseClass;
    private String place;
    private Date issueDate;
    private Date expiryDate;
    private String nationality;
    private String frontPhoto;
    private String backPhoto;
    private String statusId;
    private Date CreateAt;
}
