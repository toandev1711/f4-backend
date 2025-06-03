package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "booking")
public class BookingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingDetailId;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private String pickupAddress;
    private String dropoffAddress;

    @ManyToOne
    private VehicleType vehicleType;

    private Double price;
    private Double weight;
    private Double size;
    private String image;
    private String discountCode;
    private String descriptionNotes;

}
