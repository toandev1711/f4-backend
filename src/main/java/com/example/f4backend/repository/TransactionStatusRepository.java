package com.example.f4backend.repository;

import com.example.f4backend.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, Integer> {
    Optional<TransactionStatus> findByStatusName(String statusName);
    Optional<TransactionStatus> findByTransactionStatusId(Integer transactionStatusId);
}