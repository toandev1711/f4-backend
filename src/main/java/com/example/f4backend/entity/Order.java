package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "order_booking")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "issue_date")
    private Date issueDate;

    @ManyToOne
    @JoinColumn(name = "order_status_id", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "creation_datetime")
    private Date creationDatetime;

    @Column(name = "notes")
    private String notes;
}