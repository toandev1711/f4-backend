package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.DriverResponse;
import com.example.f4backend.dto.reponse.MessageResponse;
import com.example.f4backend.dto.reponse.PartnerWithMessagesResponse;
import com.example.f4backend.dto.request.MessageRequest;
import com.example.f4backend.entity.Driver;
import com.example.f4backend.entity.Message;
import com.example.f4backend.entity.User;
import com.example.f4backend.mapper.MessageMapper;
import com.example.f4backend.repository.DriverRepository;
import com.example.f4backend.repository.MessageRepository;
import com.example.f4backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final RoomService chatRoomService;
    private final MessageMapper messageMapper;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

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
        var chatId = chatRoomService.getChatRoomId(senderId, receiverId, true);
        if (chatId.isPresent()) {
            System.out.println("Tìm theo chatId: " + chatId.get());
            List<Message> messageList = messageRepository.findByChatId(chatId.get());
            return messageList;
        }
        return new ArrayList<>();
    }

    public List<DriverResponse> getChatPartners(String senderId) {
        List<String> ids = messageRepository.findChatPartners(senderId);
        List<DriverResponse> drivers = new ArrayList<>();
        for(String id :ids){
            Boolean dr = driverRepository.existsByDriverId(id);
            Driver driver = driverRepository.findByDriverId(id);
            drivers.add(DriverResponse.builder().driverId(driver.getDriverId())
                    .fullName(driver.getFullName())
                    .build());
        }
        return drivers;
    }

    public List<PartnerWithMessagesResponse> getChatPartnersWithMessages(String senderId) {
        List<String> partnerIds = messageRepository.findChatPartners(senderId);
        List<PartnerWithMessagesResponse> result = new ArrayList<>();
        boolean isHas = false;
        for (String partnerId : partnerIds) {
            Driver driver = driverRepository.findByDriverId(partnerId);
            if (driver != null) {
                isHas = true;
                List<Message> messages = getChatMessage(senderId, partnerId);
                result.add(
                        PartnerWithMessagesResponse.builder()
                                .partnerId(driver.getDriverId())
                                .fullName(driver.getFullName())
                                .messages(messages)
                                .build()
                );
            }
        }

        if(!isHas) {
            for (String partnerId : partnerIds) {
                User user = userRepository.findById(partnerId).orElseThrow();
                if (user != null) {
                    List<Message> messages = getChatMessage(senderId, partnerId);
                    result.add(
                            PartnerWithMessagesResponse.builder()
                                    .partnerId(user.getId())
                                    .fullName(user.getFullName())
                                    .messages(messages)
                                    .build()
                    );
                }
            }
        }
        return result;
    }
}