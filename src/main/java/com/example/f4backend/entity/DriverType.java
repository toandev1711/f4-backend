package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "driver_type")
public class DriverType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driverTypeId;

    @Column(nullable = false)
    private String driverTypeName;
}