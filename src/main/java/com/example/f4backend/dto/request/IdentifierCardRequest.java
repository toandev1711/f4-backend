package com.example.f4backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.sql.Date;

@Data
public class IdentifierCardRequest {
    @NotBlank(message = "LICENSE_NUMBER_NOT_BLANK")
    private String identifierNumber;

    @NotNull(message = "issueDate không được để trống")
    @PastOrPresent(message = "issueDate phải là ngày hiện tại hoặc trong quá khứ")
    private Date issueDate;

    @NotBlank(message = "frontPhoto không được để trống")
    private String frontPhoto;

    @NotBlank(message = "backPhoto không được để trống")
    private String backPhoto;

    private Integer statusId;

    private Date createAt;

}
