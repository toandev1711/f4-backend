package com.example.f4backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "transaction_status")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  transactionStatusId;

    @Column(nullable = false)
    private String statusName;
}


