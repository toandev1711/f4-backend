package com.example.f4backend.repository;

import com.example.f4backend.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
    Optional<VehicleType> findById(int vehicleTypeId);
}

