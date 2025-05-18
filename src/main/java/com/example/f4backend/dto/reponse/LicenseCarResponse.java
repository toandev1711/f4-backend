package com.example.f4backend.dto.reponse;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LicenseCarResponse {
    private String licenseCarId;
    private String driverId;
    private String licenseNumber;
    private String licenseClass;
    private String place;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String nationality;
    private String frontPhoto;
    private String backPhoto;
    private LocalDate createAt;
    private String statusName;
}
