package com.example.f4backend.dto.reponse;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepositResponse {
    private String transactionId;
    private BigDecimal amount;
    private String type; // "DEPOSIT"
    private String status;
    private LocalDateTime transactionTime;
}
