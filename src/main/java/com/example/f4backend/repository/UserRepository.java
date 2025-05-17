package com.example.f4backend.repository;

import com.example.f4backend.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface    UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsById(@NotNull String id);

    Optional<User> findByUsername(String username);
    Optional<User> findByPhone(String phone);

}
