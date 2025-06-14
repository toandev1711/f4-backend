package com.example.f4backend.mapper;

import com.example.f4backend.dto.reponse.WalletResponse;
import com.example.f4backend.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    @Mapping(target = "driverId", source = "driver.driverId")
    WalletResponse toWalletResponse(Wallet wallet);
}
