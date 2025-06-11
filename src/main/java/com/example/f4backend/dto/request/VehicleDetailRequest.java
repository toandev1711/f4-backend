package com.example.f4backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.sql.Date;

@Data
public class VehicleDetailRequest {
//    @NotBlank(message = "LICENSE_NUMBER_NOT_BLANK")
    @NotBlank(message = "licensePlateNumber không được để trống")
    private String licensePlateNumber;

    @NotBlank(message = "ownerName không được để trống")
    private String ownerName;

    private String brand;

    private String engineNumber;

    @NotBlank(message = "frontPhoto không được để trống")
    private String frontPhoto;

    @NotBlank(message = "backPhoto không được để trống")
    private String backPhoto;

    private Date issueDate;

    private Integer statusId;

    private Integer vehicleTypeId;

    private Date createAt;
}
