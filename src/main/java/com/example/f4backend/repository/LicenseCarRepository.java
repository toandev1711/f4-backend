package com.example.f4backend.repository;

import com.example.f4backend.entity.LicenseCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenseCarRepository extends JpaRepository<LicenseCar, String> {
    Optional<LicenseCar> findByDriverDriverId(String driverId);
}
