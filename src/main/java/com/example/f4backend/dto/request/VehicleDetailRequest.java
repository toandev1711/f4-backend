package com.example.f4backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;
@Data
public class VehicleDetailRequest {
    @NotBlank(message = "LICENSE_NUMBER_NOT_BLANK")
    private String licensePlateNumber;
    private String ownerName;
    private String brand;
    private String engineNumber;
    private String frontPhoto;
    private String backPhoto;
    private Date issueDate;
    private String statusId;
}
