package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "delivery_detail")
@Getter
@Setter
public class DeliveryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_detail_id")
    private String deliveryDetailId;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "pickup_address", nullable = false)
    private String pickupAddress;

    @Column(name = "dropoff_address", nullable = false)
    private String dropoffAddress;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "package_type")
    private String packageType;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "size")
    private String size;

    @Column(name = "image")
    private String image;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "description_notes")
    private String descriptionNotes;

    @OneToOne(mappedBy = "deliveryDetail", cascade = CascadeType.ALL)
    private Coordinates coordinates;
}