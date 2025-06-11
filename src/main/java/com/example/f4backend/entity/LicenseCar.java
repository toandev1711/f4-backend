package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.sql.Date;

@Entity
@Data
@Table(name = "license_cars")
public class LicenseCar {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String licenseCarId;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private String licenseNumber;

    @Column(nullable = false)
    private String licenseClass;

    @Column(nullable = false)
    private String place;

    private Date issueDate;

    private Date expiryDate;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private String frontPhoto;

    @Column(nullable = false)
    private String backPhoto;

    @Column(nullable = false)
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private DocumentStatus status;
}
