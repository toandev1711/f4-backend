package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_status")
@Getter
@Setter
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;

    @Column(name = "status_name", nullable = false, unique = true)
    private String statusName;
}