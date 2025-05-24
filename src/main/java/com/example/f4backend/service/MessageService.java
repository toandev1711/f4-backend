package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.MessageResponse;
import com.example.f4backend.dto.request.MessageRequest;
import com.example.f4backend.entity.Message;
import com.example.f4backend.mapper.MessageMapper;
import com.example.f4backend.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final RoomService chatRoomService;
    private final MessageMapper messageMapper;

    public MessageResponse save(MessageRequest request) {
        var chatId = chatRoomService
                .getChatRoomId(request.getSenderId(), request.getReceiverId(), true)
                .orElseThrow(() -> new RuntimeException("Chat room could not be created or found"));

        Message message = messageMapper.toMessage(request);
        message.setChatId(chatId);
        message.setTimestamp(new Date());
        messageRepository.save(message);
        return MessageResponse.builder()
                .chatId(chatId)
                .timestamp(new Date())
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .content(request.getContent())
                .build();
    }

    public List<Message> getChatMessage(String senderId, String receiverId) {
        var chatId = chatRoomService.getChatRoomId(senderId, receiverId, false);
        return chatId.map(messageRepository::findByChatId).orElse(new ArrayList<>());
    }
}