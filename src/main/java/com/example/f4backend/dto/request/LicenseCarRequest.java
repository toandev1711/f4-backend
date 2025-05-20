package com.example.f4backend.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.sql.Date;

@Data
public class LicenseCarRequest {
//    @NotBlank(message = "LICENSE_NUMBER_NOT_BLANK")
    @NotBlank(message = "licenseNumber không được để trống")
    private String licenseNumber;

    @NotBlank(message = "licenseClass không được để trống")
    private String licenseClass;

    @NotBlank(message = "place không được để trống")
    private String place;

    @NotNull(message = "issueDate không được để trống")
    @PastOrPresent(message = "issueDate phải là ngày hiện tại hoặc trong quá khứ")
    private Date issueDate;

    @NotNull(message = "expiryDate không được để trống")
    @Future(message = "expiryDate phải là ngày trong tương lai")
    private Date expiryDate;

    @NotBlank(message = "nationality không được để trống")
    private String nationality;

    @NotBlank(message = "frontPhoto không được để trống")
    private String frontPhoto;

    @NotBlank(message = "backPhoto không được để trống")
    private String backPhoto;

    private String statusId;

    private Date CreateAt;
}
