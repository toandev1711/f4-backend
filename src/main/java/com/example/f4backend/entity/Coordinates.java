package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "coordinates")
@Getter
@Setter
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "coordinate_id")
    private String coordinateId;

    @OneToOne
    @JoinColumn(name = "delivery_detail_id", nullable = false, unique = true)
    private DeliveryDetail deliveryDetail;

    @Column(name = "pickup_latitude", nullable = false ,precision = 9, scale = 6)
    private BigDecimal pickupLatitude;

    @Column(name = "pickup_longitude", nullable = false ,precision = 9, scale = 6)
    private BigDecimal pickupLongitude;

    @Column(name = "dropoff_latitude", nullable = false , precision = 9, scale = 6)
    private BigDecimal dropoffLatitude;

    @Column(name = "dropoff_longitude", nullable = false , precision = 9, scale = 6)
    private BigDecimal dropoffLongitude;
}