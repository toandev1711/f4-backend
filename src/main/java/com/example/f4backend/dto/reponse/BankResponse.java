package com.example.f4backend.dto.reponse;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BankResponse {
    private String driverId;
    private String bankName;
    private String bankAccountNumber;
    private String accountOwnerName;
    private LocalDateTime createAt;
}
