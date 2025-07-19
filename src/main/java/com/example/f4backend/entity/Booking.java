package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@ToString(exclude = "bookingDetails")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Driver driver;

    private String orderType;

    @ManyToOne
    private OrderStatus orderStatus;

    private LocalDateTime createdDate;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private BookingDetail bookingDetail;

    @ManyToOne
    @JoinColumn(name = "booking_status_id")
    private BookingStatus bookingStatus;

    private double rating;
    private String ratingNote;
}
