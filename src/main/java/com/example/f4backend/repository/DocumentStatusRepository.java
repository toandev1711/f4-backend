package com.example.f4backend.repository;

import com.example.f4backend.entity.DocumentStatus;
import com.example.f4backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentStatusRepository extends JpaRepository<DocumentStatus, Integer> {
    Optional<DocumentStatus> findById(int statusId);
}
