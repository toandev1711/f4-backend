package com.example.f4backend.repository;

import com.example.f4backend.entity.VehicleDetail;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleDetailRepository extends JpaRepository<VehicleDetail, Integer> {
      List<VehicleDetail> findByDriverDriverId(String driverId);
      boolean existsByLicensePlateNumber(@NotNull String licensePlateNumber);
}
