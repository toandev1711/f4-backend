package com.example.f4backend.repository;

import com.example.f4backend.entity.Driver;
import com.example.f4backend.entity.DriverRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DriverRevenueRepository extends JpaRepository<DriverRevenue, String> {
    Optional<DriverRevenue> findByDriverAndDate(Driver driver, LocalDate date);
    boolean existsByBookingId(String bookingId);
    @Query("SELECT COALESCE(SUM(dr.amount), 0) FROM DriverRevenue dr WHERE dr.driver.driverId = :driverId")
    BigDecimal getTotalAmountByDriverId(@Param("driverId") String driverId);
    List<DriverRevenue> findAllByDriverDriverIdAndDate(String driverId, LocalDate date);

}
