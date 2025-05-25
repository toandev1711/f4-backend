package com.example.f4backend.dto.request;

import lombok.Data;

@Data
public class MessageRequest {
    private String senderId;
    private String receiverId;
    private String content;
}
