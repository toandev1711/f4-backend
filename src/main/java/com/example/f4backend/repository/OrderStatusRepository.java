package com.example.f4backend.repository;

import com.example.f4backend.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    Optional<OrderStatus> findByStatusName(String statusName);
    Optional<OrderStatus> findByStatusId(int statusId);
}