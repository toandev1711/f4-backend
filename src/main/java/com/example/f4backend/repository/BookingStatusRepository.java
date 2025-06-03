package com.example.f4backend.repository;

import com.example.f4backend.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingStatusRepository extends JpaRepository<BookingStatus, Integer> {
    Optional<BookingStatus> findByStatusNameIgnoreCase(String statusName);

}
