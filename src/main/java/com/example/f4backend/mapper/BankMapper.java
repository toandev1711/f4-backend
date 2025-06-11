package com.example.f4backend.mapper;

import com.example.f4backend.dto.reponse.BankResponse;
import com.example.f4backend.dto.request.BankRequest;
import com.example.f4backend.entity.Bank;
import com.example.f4backend.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankMapper {
    @Mapping(target = "createAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(source = "driver", target = "driver")
    @Mapping(target = "bankId", ignore = true)
    Bank toBank(BankRequest bankRequest , Driver driver);

    @Mapping(target = "driverId", source = "driver.driverId")
    BankResponse toBankResponse(Bank bank);

    //update
//    @Mapping(target = "bankId", source = "bankId")
    @Mapping(target = "createAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(source = "driver", target = "driver")
    Bank toUpdateBank(BankRequest bankRequest , Driver driver);
}
