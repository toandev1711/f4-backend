package com.example.f4backend.dto.request;

import lombok.Data;

import java.sql.Date;

@Data
public class LicenseCarRequest {
    private String licenseNumber;
    private String licenseClass;
    private String place;
    private Date issueDate;
    private Date expiryDate;
    private String nationality;
    private String frontPhoto;
    private String backPhoto;
    private String statusId;
}
