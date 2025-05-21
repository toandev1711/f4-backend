package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "vehicle_types")
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleTypeId;

    @Column(nullable = false)
    private String vehicleTypeName;

    @Column(nullable = false)
    private Timestamp createAt;
}
