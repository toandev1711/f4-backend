package com.example.f4backend.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String driverId;
    private BigDecimal amount;

    private String bankName;
    private String bankAccountNumber;
    private String accountOwnerName;
}
