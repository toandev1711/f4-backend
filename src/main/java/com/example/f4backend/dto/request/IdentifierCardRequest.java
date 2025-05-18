package com.example.f4backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;

@Data
public class IdentifierCardRequest {
    @NotBlank(message = "LICENSE_NUMBER_NOT_BLANK")
    private String identifierNumber;
    private Date issueDate;
    private String frontPhoto;
    private String backPhoto;
    private int statusId;
}
