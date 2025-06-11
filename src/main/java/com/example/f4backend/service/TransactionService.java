package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.DepositResponse;
import com.example.f4backend.dto.reponse.WithDrawResponse;
import com.example.f4backend.dto.request.TransactionRequest;
import com.example.f4backend.entity.Transaction;
import com.example.f4backend.entity.TransactionStatus;
import com.example.f4backend.entity.TransactionType;
import com.example.f4backend.entity.Wallet;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.exception.CustomException;
import com.example.f4backend.mapper.TransactionMapper;
import com.example.f4backend.repository.TransactionRepository;
import com.example.f4backend.repository.TransactionStatusRepository;
import com.example.f4backend.repository.TransactionTypeRepository;
import com.example.f4backend.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final  WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionStatusRepository transactionStatusRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public DepositResponse deposit(TransactionRequest request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException(ErrorCode.INVALID_AMOUNT);
        }

        Wallet wallet = walletRepository.findByDriverDriverId(request.getDriverId())
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));

        TransactionType transactionType = transactionTypeRepository.findByTransactionTypeId(1) //DEPOSIT
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND));

        TransactionStatus transactionStatus = transactionStatusRepository.findByTransactionStatusId(1)
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_STATUS_NOT_FOUND));

        Transaction transaction = transactionMapper.toTransaction(request, wallet, transactionType, transactionStatus);

        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        wallet.setLastUpdated(LocalDateTime.now());
        walletRepository.save(wallet);

        transaction = transactionRepository.save(transaction);

        return transactionMapper.toDepositResponse(transaction);
    }

    @Transactional
    public WithDrawResponse withdraw(TransactionRequest request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException(ErrorCode.INVALID_AMOUNT);
        }

        Wallet wallet = walletRepository.findByDriverDriverId(request.getDriverId())
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));

        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new CustomException(ErrorCode.INSUFFICIENT_BALANCE);
        }

        TransactionType transactionType = transactionTypeRepository.findByTransactionTypeId(2) //WITHDRAW
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND));

        TransactionStatus transactionStatus = transactionStatusRepository.findByTransactionStatusId(1)
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_STATUS_NOT_FOUND));

        Transaction transaction = transactionMapper.toTransaction(request, wallet, transactionType, transactionStatus);

        wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
        wallet.setLastUpdated(LocalDateTime.now());
        walletRepository.save(wallet);

        transaction = transactionRepository.save(transaction);

        return transactionMapper.toWithDrawResponse(transaction);
    }

    @Transactional
    public DepositResponse updateDeposit(String transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_NOT_FOUND));

        //kiem tra xem co phai la deposit khong
        if(transaction.getTransactionType().getTransactionTypeId() != 1){
            throw new CustomException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
        }

        Wallet wallet = walletRepository.findById(transaction.getWallet().getWalletId())
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));

        wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
        wallet.setLastUpdated(LocalDateTime.now());
        walletRepository.save(wallet);

        transaction.setStatus(transactionStatusRepository.findByTransactionStatusId(3).
                orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_STATUS_NOT_FOUND)));
        transactionRepository.save(transaction);

        return transactionMapper.toDepositResponse(transaction);
    }

    @Transactional
    public WithDrawResponse updateWithDraw(String transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_NOT_FOUND));

        //kiem tra xem co phai la withdraw khong
        if(transaction.getTransactionType().getTransactionTypeId() != 2){
            throw new CustomException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
        }

        Wallet wallet = walletRepository.findById(transaction.getWallet().getWalletId())
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));

        wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
        wallet.setLastUpdated(LocalDateTime.now());
        walletRepository.save(wallet);

        transaction.setStatus(transactionStatusRepository.findByTransactionStatusId(3).
                orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_STATUS_NOT_FOUND)));
        transactionRepository.save(transaction);
        return transactionMapper.toWithDrawResponse(transaction);
    }

    public List<WithDrawResponse> getListTransaction(String driverId) {
        Wallet wallet = walletRepository.findByDriverDriverId(driverId)
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));

        // lây ra danh sách 10 giao dịch gần nhất
        List<Transaction> transactions = transactionRepository.findTop10ByWalletOrderByTransactionTimeDesc(wallet);
        return transactions.stream().map(transactionMapper::toWithDrawResponse).toList();
    }

    public List<WithDrawResponse> getListByTransactionType(String driverId , Integer transactionStatusId) {
        Wallet wallet = walletRepository.findByDriverDriverId(driverId)
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));

        TransactionType transactionType = transactionTypeRepository.findByTransactionTypeId(transactionStatusId)
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND));
        List<Transaction> transactions = transactionRepository.findTop10ByWalletAndTransactionTypeOrderByTransactionTimeDesc(wallet, transactionType);

        return transactions.stream().map(transactionMapper::toWithDrawResponse).toList();
    }
}
