package com.example.f4backend.repository;

import com.example.f4backend.entity.Bank;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, String> {
    List<Bank> findAllByDriver_DriverId(String driverId);

    @NotNull
    Optional<Bank> findById(@NotNull String bankId);
}
