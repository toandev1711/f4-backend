package com.example.f4backend.repository;

import com.example.f4backend.entity.LicenseCar;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LicenseCarRepository extends JpaRepository<LicenseCar, String> {
    List<LicenseCar> findByDriverDriverId(String driverId);

    boolean existsByLicenseNumber(@NotNull String licenseNumber);

    // needtofix
    Optional<LicenseCar> findByLicenseNumber(String licenseNumber);
    Optional<LicenseCar> findByLicenseCarIdAndDriverDriverId(String licenseCarId, String driverId);

}
