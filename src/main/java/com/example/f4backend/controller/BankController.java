package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.BankResponse;
import com.example.f4backend.dto.reponse.OrderResponse;
import com.example.f4backend.dto.request.BankRequest;
import com.example.f4backend.dto.request.OrderRequest;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.service.BankService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankController {
    BankService bankService;

    @PostMapping("/create/{driverId}")
    public ApiResponse<BankResponse> createOrder(@Valid @RequestBody BankRequest request , @PathVariable String driverId){
        return ApiResponse.<BankResponse>
                        builder()
                .code(ErrorCode.CREATE_BANK_SUCCESS.getCode())
                .result(bankService.createBank(request , driverId))
                .message(ErrorCode.CREATE_BANK_SUCCESS.getMessage())
                .build();
    }
}
