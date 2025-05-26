package com.example.f4backend.repository;

import com.example.f4backend.entity.Driver;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, String> {
    boolean existsByDriverId(@NotNull String driverId);

    Optional<Driver> findByUserId(String userId);

}
