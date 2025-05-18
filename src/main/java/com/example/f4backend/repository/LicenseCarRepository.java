package com.example.f4backend.repository;

import com.example.f4backend.entity.LicenseCar;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LicenseCarRepository extends JpaRepository<LicenseCar, Integer> {
    List<LicenseCar> findByDriverDriverId(String driverId);
    boolean existsByLicenseNumber(@NotNull String licenseNumber);
}
