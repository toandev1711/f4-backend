package com.example.f4backend.dto.reponse;

import lombok.Data;

@Data
public class WalletResponse {
    private String driverId;
    private String walletId;
    private String balance;
}
