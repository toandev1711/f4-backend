package com.example.f4backend.repository;

import com.example.f4backend.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface WalletRepository extends JpaRepository<Wallet, String> {
    Optional<Wallet> findByDriverDriverId(String driverId);
}