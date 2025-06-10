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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionService transactionService;

    @GetMapping("/getList/{driverId}")
    public ApiResponse<List<WithDrawResponse>> getListTransaction(@PathVariable String driverId) {
        return ApiResponse.<List<WithDrawResponse>>builder()
                .code(ErrorCode.TRANSACTION_SUCCESS.getCode())
                .result(transactionService.getListTransaction(driverId))
                .message(ErrorCode.TRANSACTION_SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/getList/{driverId}/{transactionStatusId}")
    public ApiResponse<List<WithDrawResponse>> getListByTransactionType(@PathVariable String driverId , @PathVariable Integer transactionStatusId) {
        return ApiResponse.<List<WithDrawResponse>>builder()
                .code(ErrorCode.TRANSACTION_SUCCESS.getCode())
                .result(transactionService.getListByTransactionType(driverId , transactionStatusId))
                .message(ErrorCode.TRANSACTION_SUCCESS.getMessage())
                .build();
    }

    @PostMapping("/deposit")
    public ApiResponse<DepositResponse> deposit(@Valid @RequestBody TransactionRequest request) {
        return ApiResponse.<DepositResponse>builder()
                .code(ErrorCode.CREATE_DRIVER_SUCCESS.getCode())
                .result(transactionService.deposit(request))
                .message(ErrorCode.CREATE_DRIVER_SUCCESS.getMessage())
                .build();
    }

    @PutMapping("/update-deposit/{transactionId}")
    public ApiResponse<DepositResponse> updateDeposit(@PathVariable String transactionId) {
        return ApiResponse.<DepositResponse>builder()
                .code(ErrorCode.TRANSACTION_SUCCESS.getCode())
                .result(transactionService.updateDeposit(transactionId))
                .message(ErrorCode.TRANSACTION_SUCCESS.getMessage())
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

    @PutMapping("/update-withdraw/{transactionId}")
    public ApiResponse<WithDrawResponse> updateWithdraw(@PathVariable String transactionId) {
        return ApiResponse.<WithDrawResponse>builder()
                .code(ErrorCode.TRANSACTION_SUCCESS.getCode())
                .result(transactionService.updateWithDraw(transactionId))
                .message(ErrorCode.TRANSACTION_SUCCESS.getMessage())
                .build();
    }
}
