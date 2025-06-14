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
    @Mapping(target = "transactionTime", expression = "java(java.time.LocalDateTime.now())")
    Transaction toTransaction(TransactionRequest request, Wallet wallet, TransactionType transactionType, TransactionStatus transactionStatus);


    @Mapping(target = "type", source = "transactionType.typeName")
    @Mapping(target = "status", source = "transactionStatus.statusName")
    DepositResponse toDepositResponse(Transaction transaction);


    @Mapping(target = "typeName", source = "transactionType.typeName")
    @Mapping(target = "statusName", source = "transactionStatus.statusName")
    WithDrawResponse toWithDrawResponse(Transaction transaction);
}
