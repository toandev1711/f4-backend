package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "driver_revenue", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"bookingId"})
})
public class DriverRevenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", referencedColumnName = "driverId", nullable = false)
    private Driver driver;

    @Column(name = "bookingId", nullable = false, unique = true)
    private String bookingId;

    private LocalDate date;

    private BigDecimal amount;

    // getters và setters
}