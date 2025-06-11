package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.BankResponse;
import com.example.f4backend.dto.request.BankRequest;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.service.BankService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankController {
    BankService bankService;

    @PostMapping("/create/{driverId}")
    public ApiResponse<BankResponse> createBank(@Valid @RequestBody BankRequest request , @PathVariable String driverId){
        return ApiResponse.<BankResponse>
                        builder()
                .code(ErrorCode.CREATE_BANK_SUCCESS.getCode())
                .result(bankService.createBank(request , driverId))
                .message(ErrorCode.CREATE_BANK_SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/getList/{driverId}")
    public ApiResponse<List<BankResponse>> getListBankInfo(@PathVariable String driverId){
        return ApiResponse.<List<BankResponse>>
                        builder()
                .code(ErrorCode.BANK_GET_SUCCESS.getCode())
                .result(bankService.getBanksByDriverId(driverId))
                .message(ErrorCode.BANK_GET_SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/getDetail/{bankId}")
    public ApiResponse<BankResponse> getBankInfo(@PathVariable String bankId){
        return ApiResponse.<BankResponse>
                        builder()
                .code(ErrorCode.BANK_GET_SUCCESS.getCode())
                .result(bankService.getBankById(bankId))
                .message(ErrorCode.BANK_GET_SUCCESS.getMessage())
                .build();
    }

    @PutMapping("/update/{bankId}")
    public ApiResponse<BankResponse> updateBankInfo(@Valid @RequestBody BankRequest request , @PathVariable String bankId){
        return ApiResponse.<BankResponse>
                        builder()
                .code(ErrorCode.BANK_UPDATE_SUCCESS.getCode())
                .result(bankService.updateBank(bankId, request))
                .message(ErrorCode.BANK_UPDATE_SUCCESS.getMessage())
                .build();
    }

    @DeleteMapping("/delete/{bankId}")
    public ApiResponse<BankResponse> deleteBankInfo(@PathVariable String bankId){
        bankService.deleteBank(bankId);
        return ApiResponse.<BankResponse>
                        builder()
                .code(ErrorCode.BANK_DELETE_SUCCESS.getCode())
                .message(ErrorCode.BANK_DELETE_SUCCESS.getMessage())
                .build();
    }
}
