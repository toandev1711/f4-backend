package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.DepositResponse;
import com.example.f4backend.dto.reponse.DriverResponse;
import com.example.f4backend.dto.reponse.WithDrawResponse;
import com.example.f4backend.dto.request.TransactionRequest;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionService transactionService;

    @PostMapping("/deposit")
    public ApiResponse<DepositResponse> deposit(@Valid @RequestBody TransactionRequest request) {
        return ApiResponse.<DepositResponse>builder()
                .code(ErrorCode.CREATE_DRIVER_SUCCESS.getCode())
                .result(transactionService.deposit(request))
                .message(ErrorCode.CREATE_DRIVER_SUCCESS.getMessage())
                .build();
    }

    @PostMapping("/withdraw")
    public ApiResponse<WithDrawResponse> withdraw(@Valid @RequestBody TransactionRequest request) {
        return ApiResponse.<WithDrawResponse>builder()
                .code(ErrorCode.CREATE_DRIVER_SUCCESS.getCode())
                .result(transactionService.withdraw(request))
                .message(ErrorCode.CREATE_DRIVER_SUCCESS.getMessage())
                .build();
    }
}
