package com.example.f4backend.repository;

import com.example.f4backend.entity.Booking;
import com.example.f4backend.entity.Driver;
import com.example.f4backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUser(User user);
    List<Booking> findByDriver(Driver driver);
}
