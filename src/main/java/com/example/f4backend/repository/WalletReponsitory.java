package com.example.f4backend.repository;

import com.example.f4backend.entity.VehicleType;
import com.example.f4backend.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletReponsitory extends JpaRepository<Wallet, String> {
}
