package com.example.f4backend.service;


import com.example.f4backend.repository.*;

public class WalletService {
     WalletReponsitory walletReponsitory;
     TransactionRepository transactionRepository;
     TransactionTypeRepository transactionTypeRepository;
     TransactionStatusRepository transactionStatusRepository;
     DriverRepository driverRepository;

//    public Object handleTransaction(TransactionRequest dto) {
//        Driver driver = driverRepository.findById(dto.getDriverId())
//                .orElseThrow(() -> new RuntimeException("Driver not found"));
//
//        Wallet wallet = driver.getWallet();
//        if (wallet == null) throw new RuntimeException("Wallet not found");
//
//        TransactionType type = transactionTypeRepository.findByName(dto.getTransactionType())
//                .orElseThrow(() -> new RuntimeException("Transaction type not found"));
//
//        TransactionStatus status = transactionStatusRepository.findByName("SUCCESS")
//                .orElseThrow(() -> new RuntimeException("Transaction status not found"));
//
//        BigDecimal newBalance;
//        if (type.getName().equalsIgnoreCase("WITHDRAW")) {
//            if (wallet.getBalance().compareTo(dto.getAmount()) < 0)
//                throw new RuntimeException("Insufficient balance");
//            newBalance = wallet.getBalance().subtract(dto.getAmount());
//        } else if (type.getName().equalsIgnoreCase("DEPOSIT")) {
//            newBalance = wallet.getBalance().add(dto.getAmount());
//        } else {
//            throw new RuntimeException("Invalid transaction type");
//        }
//
//        Transaction tx = Transaction.builder()
//                .wallet(wallet)
//                .transactionType(type)
//                .transactionStatus(status)
//                .amount(dto.getAmount())
//                .description(dto.getDescription())
//                .timestamp(LocalDateTime.now())
//                .bankName(dto.getBankName())
//                .bankAccountNumber(dto.getBankAccountNumber())
//                .accountOwnerName(dto.getAccountOwnerName())
//                .build();
//
//        transactionRepository.save(tx);
//        wallet.setBalance(newBalance);
//        walletRepository.save(wallet);
//
//        if (type.getName().equalsIgnoreCase("WITHDRAW")) {
//            return walletMapper.toWithdrawResponse(tx, newBalance);
//        } else {
//            return walletMapper.toDepositResponse(tx, newBalance);
//        }
//    }
}
