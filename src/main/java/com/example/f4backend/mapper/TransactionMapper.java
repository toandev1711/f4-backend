package com.example.f4backend.mapper;

import com.example.f4backend.dto.reponse.DepositResponse;
import com.example.f4backend.dto.reponse.TransactionManagerResponse;
import com.example.f4backend.dto.reponse.WithDrawResponse;
import com.example.f4backend.dto.request.TransactionRequest;
import com.example.f4backend.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "transactionTime", expression = "java(java.time.LocalDateTime.now())")
    Transaction toTransaction(TransactionRequest request, Wallet wallet, TransactionType transactionType, TransactionStatus transactionStatus);


    @Mapping(target = "type", source = "transactionType.typeName")
    @Mapping(target = "status", source = "transactionStatus.statusName")
    DepositResponse toDepositResponse(Transaction transaction);


    @Mapping(target = "typeName", source = "transactionType.typeName")
    @Mapping(target = "statusName", source = "transactionStatus.statusName")
    WithDrawResponse toWithDrawResponse(Transaction transaction);

    @Mapping(target = "driverId", source = "driver.driverId")
    @Mapping(target = "typeName", source = "transactionType.typeName")
    @Mapping(target = "statusName", source = "transactionStatus.statusName")
    @Mapping(target = "phone", source = "driver.phone")
    @Mapping(target = "driverName", source = "driver.fullName")
    @Mapping(target = "driverAvatar", source = "driver.portrait")
    TransactionManagerResponse toTransactionManagerResponse(Transaction transaction , Driver driver , TransactionType transactionType , TransactionStatus transactionStatus);
}
