package com.example.f4backend.repository;

import com.example.f4backend.entity.Transaction;
import com.example.f4backend.entity.TransactionType;
import com.example.f4backend.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Override
    Optional<Transaction> findById(String s);
    List<Transaction> findByWallet(Wallet wallet);
    List<Transaction> findTop10ByWalletAndTransactionTypeOrderByTransactionTimeDesc(Wallet wallet, TransactionType transactionType);
    List<Transaction> findTop10ByWalletOrderByTransactionTimeDesc(Wallet wallet);

    @Query("SELECT t FROM Transaction t " +
            "JOIN t.wallet w " +
            "JOIN w.driver d " +
            "WHERE (:statusId = -1 OR t.transactionStatus.transactionStatusId = :statusId) " +
            "AND (:searchTerm = '' OR " +
            "LOWER(t.bankName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(t.accountOwnerName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Transaction> findBySearchTermAndStatus(
            @Param("searchTerm") String searchTerm,
            @Param("statusId") int statusId,
            Pageable pageable);

}
