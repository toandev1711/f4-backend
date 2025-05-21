package com.example.f4backend.dto.reponse;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VehicleDetailResponse {
    private String vehicleId;
    private String driverId;
    private String licensePlateNumber;
    private String ownerName;
    private String brand;
    private String engineNumber;
    private String frontPhoto;
    private String backPhoto;
    private LocalDate issueDate;
    private LocalDate createAt;
    private String statusName;
    private String vehicleTypeName;
}
