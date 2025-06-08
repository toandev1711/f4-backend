package com.example.f4backend.dto.reponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
