package com.example.f4backend.dto.reponse;

import lombok.Data;

import java.sql.Date;

@Data
public class DriverResponse {
    private String driverId;
    private String driverTypeName;
    private String driverStatusName;
    private Boolean gender;
    private String password;
    private String fullName;
    private String portrait;
    private Date dateBirth;
    private String address;
    private String email;
    private String phone;
//    private String statusName;
    private Double averageRating;
    private Date createDate;
    private Boolean isLocked;
}
