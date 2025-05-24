package com.example.f4backend.repository;

import com.example.f4backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findByUserAIdAndUserBId(String userAId, String userBId);

    @Query("SELECT r FROM Room r WHERE (r.userAId = :id1 AND r.userBId = :id2) OR (r.userAId = :id2 AND r.userBId = :id1)")
    Optional<Room> findByUserIds(@Param("id1") String id1, @Param("id2") String id2);
}