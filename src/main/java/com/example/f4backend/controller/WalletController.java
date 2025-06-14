package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.WalletResponse;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.service.WalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController {

    WalletService walletService;

    @GetMapping("/getWallet/{driverId}")
    public ApiResponse<WalletResponse> getWallet(@PathVariable String driverId) {
        return ApiResponse.<WalletResponse>builder()
                .code(ErrorCode.WALLET_GET_SUCCESS.getCode())
                .result(walletService.getWallet(driverId))
                .message(ErrorCode.WALLET_GET_SUCCESS.getMessage())
                .build();
    }
}
