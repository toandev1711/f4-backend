package com.example.f4backend.repository;

import com.example.f4backend.entity.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverStatusRepository extends JpaRepository<DriverStatus, Integer> {
    Optional<DriverStatus> findById(int statusId);
}

