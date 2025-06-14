package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.WalletResponse;
import com.example.f4backend.entity.Wallet;
import com.example.f4backend.mapper.WalletMapper;
import com.example.f4backend.repository.WalletRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletService {
    WalletRepository walletRepository;
    WalletMapper walletMapper;

    public WalletResponse getWallet(String driverId) {
        Wallet wallet = walletRepository.findByDriverDriverId(driverId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        return walletMapper.toWalletResponse(wallet);

    }
}
