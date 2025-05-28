package com.example.f4backend.dto.reponse;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private String orderId;
    private String orderType;
    private Date issueDate;
    private String statusName;
    private Date creationDatetime;
    private String notes;
}

