package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "vehicle_details")
public class VehicleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleId;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private String licensePlateNumber;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String engineNumber;

    @Column(nullable = false)
    private String frontPhoto;

    @Column(nullable = false)
    private String backPhoto;

    @Column(nullable = false)
    private Date issueDate;

    @Column(nullable = false)
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private DocumentStatus status;
}
