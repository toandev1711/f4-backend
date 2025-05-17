package com.example.f4backend.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "document_status")
public class DocumentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusID;

    @Column(nullable = false)
    private String statusName;

    private Timestamp createDate;
}
