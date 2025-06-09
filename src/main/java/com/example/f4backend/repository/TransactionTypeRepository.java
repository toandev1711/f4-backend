package com.example.f4backend.repository;

import com.example.f4backend.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer> {
    Optional<TransactionType> findByTypeName(String typeName);
    Optional<TransactionType> findByTransactionTypeId(Integer transactionTypeId);
}
