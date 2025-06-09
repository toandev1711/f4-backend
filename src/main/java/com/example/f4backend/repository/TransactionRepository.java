package com.example.f4backend.repository;

import com.example.f4backend.entity.Transaction;
import com.example.f4backend.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Override
    Optional<Transaction> findById(String s);
}
