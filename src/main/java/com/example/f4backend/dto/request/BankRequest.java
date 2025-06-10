package com.example.f4backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BankRequest {
    @NotBlank(message = "BANK_NAME_NOT_BLANK")
    private String bankName;

    @NotBlank(message = "BANK_NUMBER_NOT_BLANK")
    private String bankAccountNumber;

    @NotBlank(message = "BANK_OWNER_NAME_NOT_BLANK")
    private String accountOwnerName;
}
