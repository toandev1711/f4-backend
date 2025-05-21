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

    @NotBlank(message = "brand không được để trống")
    private String brand;

    @NotBlank(message = "engineNumber không được để trống")
    private String engineNumber;

    @NotBlank(message = "frontPhoto không được để trống")
    private String frontPhoto;

    @NotBlank(message = "backPhoto không được để trống")
    private String backPhoto;

    @NotNull(message = "issueDate không được để trống")
    @PastOrPresent(message = "issueDate phải là ngày hiện tại hoặc trong quá khứ")
    private Date issueDate;

    private String statusId;

    private Integer vehicleTypeId;

    private Date createAt;
}
