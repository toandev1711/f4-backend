package com.example.f4backend.repository;

import com.example.f4backend.entity.Driver;

import java.util.Optional;

import com.example.f4backend.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, String> {
    boolean existsByDriverId(@NotNull String driverId);

    // Optional<User> findByPhone(String phoneNumber);
    Optional<Driver> findByPhone(String phoneNumber);

    boolean existsByPhone(String phone);
    // Optional<Driver> findByUserId(String userId);

}
