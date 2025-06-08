package com.example.f4backend.dto.request;

import java.math.BigDecimal;

public class TransactionRequest {
    private String driverId;
    private BigDecimal amount;
    private Integer transactionTypeId;

    private String bankName;
    private String bankAccountNumber;
    private String accountOwnerName;
}
