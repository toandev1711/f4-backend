package com.example.f4backend.repository;

import com.example.f4backend.entity.LicenseCar;
import com.example.f4backend.entity.VehicleDetail;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleDetailRepository extends JpaRepository<VehicleDetail, String> {
    List<VehicleDetail> findByDriverDriverId(String driverId);

    boolean existsByLicensePlateNumber(@NotNull String licensePlateNumber);
    Optional<VehicleDetail> findByVehicleIdAndDriverDriverId(String vehicleId, String driverId);
}
