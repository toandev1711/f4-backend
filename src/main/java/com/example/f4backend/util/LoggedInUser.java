package com.example.f4backend.util;

import com.example.f4backend.entity.User;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.exception.CustomException;
import com.example.f4backend.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggedInUser {
    private UserRepository userRepository;
    public User getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String identifier = context.getAuthentication().getName();
        return userRepository.findByUsername(identifier)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
    }
}