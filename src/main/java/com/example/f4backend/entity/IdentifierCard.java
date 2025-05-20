package com.example.f4backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "identifier_cards")
public class IdentifierCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String identifierId;

    @OneToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private String identifierNumber;

    @Column(nullable = false)
    private Date issueDate;

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
