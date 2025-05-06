package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.UserResponse;
import com.example.f4backend.dto.request.UserCreationRequest;
import com.example.f4backend.entity.User;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.exception.CustomException;
import com.example.f4backend.mapper.UserMapper;
import com.example.f4backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    public UserResponse createUser(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new CustomException(ErrorCode.USER_EXISTED);
            
        User user = userMapper.toUser(request);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
