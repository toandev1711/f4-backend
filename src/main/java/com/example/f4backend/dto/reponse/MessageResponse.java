package com.example.f4backend.dto.reponse;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MessageResponse {
    private String chatId;
    private String senderId;
    private String receiverId;
    private String content;
    private Date timestamp;
}
