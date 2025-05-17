package com.example.f4backend.dto.reponse;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IdentifierCardResponse {
    private String identifierId;
    private String driverId;
    private String identifierNumber;
    private LocalDate issueDate;
    private String frontPhoto;
    private String backPhoto;
    private LocalDate createAt;
    private String statusName;
}
