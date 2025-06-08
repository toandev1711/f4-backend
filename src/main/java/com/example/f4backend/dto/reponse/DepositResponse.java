package com.example.f4backend.dto.reponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositResponse {
    private String transactionId;
    private BigDecimal amount;
    private String type; // "DEPOSIT"
    private String status;
    private LocalDateTime timestamp;
}
