package com.example.f4backend.dto.reponse;

import com.example.f4backend.entity.Message;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PartnerWithMessagesResponse {
    private String partnerId;
    private String fullName;
    private List<Message> messages;
}