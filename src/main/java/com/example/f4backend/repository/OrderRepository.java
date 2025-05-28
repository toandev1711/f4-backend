package com.example.f4backend.repository;

import com.example.f4backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}