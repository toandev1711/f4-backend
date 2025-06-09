package com.example.f4backend.dto.reponse;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WithDrawResponse {
    private String transactionId;
    private BigDecimal amount;
    private String typeName;
    private String bankName;
    private String bankAccountNumber;
    private String accountOwnerName;
    private String statusName;
    private LocalDateTime timestamp;
}
