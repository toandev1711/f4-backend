package com.example.f4backend.dto.request;

import lombok.Data;

import java.sql.Date;

@Data
public class IdentifierCardRequest {
    private String identifierNumber;
    private Date issueDate;
    private String frontPhoto;
    private String backPhoto;
    private String statusId;
    private Date createAt;
}
