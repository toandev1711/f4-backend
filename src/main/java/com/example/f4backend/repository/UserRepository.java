package com.example.f4backend.repository;

import com.example.f4backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    Optional<User> findByUsername(String username);
    Optional<User> findByPhone(String phoneNumber);
    @Query("SELECT u.userNumber FROM User u ORDER BY u.userNumber DESC LIMIT 1")
    String findLastUserNumber();
}
