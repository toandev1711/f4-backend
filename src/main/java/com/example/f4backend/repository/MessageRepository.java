package com.example.f4backend.repository;

import com.example.f4backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findByChatId(String chatId);
    @Query("SELECT DISTINCT m.receiverId FROM Message m WHERE m.senderId = :senderId " +
            "UNION " +
            "SELECT DISTINCT m.senderId FROM Message m WHERE m.receiverId = :senderId")
    List<String> findChatPartners(@Param("senderId") String senderId);
}
