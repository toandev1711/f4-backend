package com.example.f4backend.repository;

import com.example.f4backend.entity.Driver;
import com.example.f4backend.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, String> {
    boolean existsByDriverId(@NotNull String driverId);

    Optional<Driver> findByUser(User user);
}
