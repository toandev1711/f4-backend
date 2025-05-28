package com.example.f4backend.dto.request;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class OrderRequest {
    private String orderType;
    private Date issueDate;
    private int statusId;
    private String notes;
}