package com.example.f4backend.dto.reponse;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionManagerResponse {
    private String transactionId;
    private String driverId;
    private String driverName;
    private String driverAvatar;
    private String phone;

    private String typeName;
    private BigDecimal amount;
    private String statusName;

    private String bankName;
    private String bankAccountNumber;
    private String accountOwnerName;
    private LocalDateTime transactionTime;

}
