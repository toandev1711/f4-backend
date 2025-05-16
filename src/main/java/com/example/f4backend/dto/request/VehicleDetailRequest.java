package com.example.f4backend.dto.request;

import lombok.Data;

import java.sql.Date;
@Data
public class VehicleDetailRequest {
    private String licensePlateNumber;
    private String ownerName;
    private String brand;
    private String engineNumber;
    private String frontPhoto;
    private String backPhoto;
    private Date issueDate;
    private String statusId;
}
