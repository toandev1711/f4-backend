package com.example.f4backend.repository;

import com.example.f4backend.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidtokenRepository extends JpaRepository<InvalidToken, String> {
}
