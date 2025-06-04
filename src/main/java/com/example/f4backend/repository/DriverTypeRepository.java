package com.example.f4backend.repository;

import com.example.f4backend.entity.DriverType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverTypeRepository extends JpaRepository<DriverType, Integer> {
    Optional<DriverType> findById(int statusId);
}
