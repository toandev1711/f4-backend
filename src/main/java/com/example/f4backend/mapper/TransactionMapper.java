package com.example.f4backend.mapper;

import com.example.f4backend.dto.reponse.DepositResponse;
import com.example.f4backend.dto.reponse.WithDrawResponse;
import com.example.f4backend.dto.request.TransactionRequest;
import com.example.f4backend.entity.Transaction;
import com.example.f4backend.entity.TransactionStatus;
import com.example.f4backend.entity.TransactionType;
import com.example.f4backend.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "wallet", source = "wallet")
    @Mapping(target = "transactionType", source = "transactionType")
    @Mapping(target = "amount", source = "request.amount")
    @Mapping(target = "bankName", source = "request.bankName")
    @Mapping(target = "bankAccountNumber", source = "request.bankAccountNumber")
    @Mapping(target = "accountOwnerName", source = "request.accountOwnerName")
    @Mapping(target = "transactionTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", source = "transactionStatus")
    Transaction toTransaction(TransactionRequest request, Wallet wallet, TransactionType transactionType, TransactionStatus transactionStatus);

    @Mapping(target = "transactionId", source = "transactionId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "type", source = "transactionType.typeName")
    @Mapping(target = "status", source = "status.statusName")
    @Mapping(target = "timestamp", source = "transactionTime")
    DepositResponse toDepositResponse(Transaction transaction);

    @Mapping(target = "transactionId", source = "transactionId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "typeName", source = "transactionType.typeName")
    @Mapping(target = "bankName", source = "bankName")
    @Mapping(target = "bankAccountNumber", source = "bankAccountNumber")
    @Mapping(target = "accountOwnerName", source = "accountOwnerName")
    @Mapping(target = "statusName", source = "status.statusName")
    @Mapping(target = "timestamp", source = "transactionTime")
    WithDrawResponse toWithDrawResponse(Transaction transaction);
}
