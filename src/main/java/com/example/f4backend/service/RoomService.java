package com.example.f4backend.service;

import java.util.Optional;

import com.example.f4backend.entity.Room;
import com.example.f4backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Optional<String> getChatRoomId(String user1, String user2, boolean createIfNotExist) {
        String userA = user1.compareTo(user2) < 0 ? user1 : user2;
        String userB = user1.compareTo(user2) < 0 ? user2 : user1;

        Optional<Room> room = roomRepository.findByUserAIdAndUserBId(userA, userB);
        if (room.isPresent()) {
            return Optional.of(room.get().getChatId());
        }

        if (createIfNotExist) {
            String chatId = userA + "_" + userB;
            Room newRoom = Room.builder()
                    .chatId(chatId)
                    .userAId(userA)
                    .userBId(userB)
                    .build();
            roomRepository.save(newRoom);
            return Optional.of(chatId);
        }

        return Optional.empty();
    }
}