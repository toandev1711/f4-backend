package com.example.f4backend.entity;
import com.example.f4backend.enums.DriverStatus;
import com.example.f4backend.enums.DriverType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String driverId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverType driverType;

    @Column(nullable = false, columnDefinition = "decimal(10,2)")
    private Double priceMoney;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverStatus driverStatus;

    @OneToOne(mappedBy = "driver")
    private IdentifierCard identifierCard;

    @OneToMany(mappedBy = "driver")
    private List<LicenseCar> licenseCars;

    @OneToMany(mappedBy = "driver")
    private List<VehicleDetail> vehicleDetails;
}
