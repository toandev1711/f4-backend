package com.example.f4backend.repository;

import com.example.f4backend.entity.IdentifierCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdentifierCardRepository extends JpaRepository<IdentifierCard, Integer> {
    Optional<IdentifierCard> findByDriverDriverId(String driverId);
}
