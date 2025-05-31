package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "driver_status")
public class DriverStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driverStatusId;

    @Column(nullable = false)
    private String driverStatusName;
}
