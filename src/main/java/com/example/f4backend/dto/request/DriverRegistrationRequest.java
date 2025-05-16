package com.example.f4backend.dto.request;

import lombok.Data;

@Data
public class DriverRegistrationRequest {
    private String email;
    private String phone;
    private Boolean gender;
    private String password;
    private String fullName;
    private String photo;
    private java.sql.Date dateBirth;
    private String address;
    private Boolean isLocked;
    private String driverType;
    private String driverStatus;
    private Double priceMoney;
    private IdentifierCardRequest identifierCard;
    private VehicleDetailRequest vehicle;
    private LicenseCarRequest license;

//    private List<VehicleDetailRequest> vehicles;
//    private List<LicenseCarRequest> licenses;
}
