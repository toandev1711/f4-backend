package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.BankResponse;
import com.example.f4backend.dto.request.BankRequest;
import com.example.f4backend.entity.Bank;
import com.example.f4backend.entity.Driver;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.exception.CustomException;
import com.example.f4backend.mapper.BankMapper;
import com.example.f4backend.repository.BankRepository;
import com.example.f4backend.repository.DriverRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankService {
    DriverRepository driverRepository;
    BankRepository bankRepository;
    BankMapper bankMapper;

    @Transactional
    public BankResponse createBank(BankRequest request , String driverId) {
        // Check if driver exists
        Driver driver = driverRepository.findById(driverId)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));

        List<Bank> existingBanks = bankRepository.findAllByDriver_DriverId(driverId);

        boolean isDuplicate = existingBanks.stream().anyMatch(bank ->
                bank.getBankName().equalsIgnoreCase(request.getBankName()) &&
                        bank.getBankAccountNumber().equals(request.getBankAccountNumber()) &&
                        bank.getAccountOwnerName().equalsIgnoreCase(request.getAccountOwnerName())
        );
        if (isDuplicate) {
            throw new CustomException(ErrorCode.BANK_ALREADY_EXISTS);
        }
        if (existingBanks.size() >= 3) {
            throw new CustomException(ErrorCode.BANK_LIMIT_EXCEEDED);
        }

        Bank bank = bankMapper.toBank(request, driver);
        bank.setDriver(driver);

        return bankMapper.toBankResponse(bankRepository.save(bank));
    }

    public List<BankResponse> getBanksByDriverId(String driverId) {
        List<Bank> banks = bankRepository.findAllByDriver_DriverId(driverId);
        return banks.stream().map(bankMapper::toBankResponse).toList();
    }

    public BankResponse getBankById(String bankId) {
        Optional<Bank> optionalBank = bankRepository.findById(bankId);
        if (optionalBank.isEmpty()) {
            throw new CustomException(ErrorCode.BANK_NOT_FOUND);
        }
        return bankMapper.toBankResponse(optionalBank.get());
    }

    public BankResponse updateBank(String bankId, BankRequest request) {
        Optional<Bank> optionalBank = bankRepository.findById(bankId);

        if (optionalBank.isEmpty()) {
            throw new CustomException(ErrorCode.BANK_NOT_FOUND);
        }

        Bank bank = bankMapper.toUpdateBank(request, optionalBank.get().getDriver());
        bank.setBankId(bankId);

        return bankMapper.toBankResponse(bankRepository.save(bank));
    }

    public void deleteBank(String bankId) {
        bankRepository.deleteById(bankId);
    }
}
