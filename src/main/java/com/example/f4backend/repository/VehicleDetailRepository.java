package com.example.f4backend.repository;

import com.example.f4backend.entity.VehicleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleDetailRepository extends JpaRepository<VehicleDetail, String> {
    Optional<VehicleDetail> findByDriverDriverId(String driverId);
}
