package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.UserResponse;
import com.example.f4backend.dto.request.UserCreationRequest;
import com.example.f4backend.dto.request.UserUpdateRequest;
import com.example.f4backend.entity.Role;
import com.example.f4backend.entity.User;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.exception.CustomException;
import com.example.f4backend.mapper.UserMapper;
import com.example.f4backend.repository.RoleRepository;
import com.example.f4backend.repository.UserRepository;
import com.example.f4backend.util.LoggedInUser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    private final RoleRepository roleRepository;
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    @Autowired
    LoggedInUser loggedInUser;
    public UserResponse createUser(UserCreationRequest request){
        String lastUserNumber = userRepository.findLastUserNumber();
        if (userRepository.existsByPhone(request.getPhone()))
            throw new CustomException(ErrorCode.USER_EXISTED);
        int nextNumber = 1;

        if (lastUserNumber != null) {
            nextNumber = Integer.parseInt(lastUserNumber) + 1;
        }
        Role userRole = new Role();
        if(request.isDriver())
        {
             userRole = roleRepository.findByName("USER")
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName("USER");
                        newRole.setDescription("Only for user");
                        return roleRepository.save(newRole);
                    });
        }
        else{
             userRole = roleRepository.findByName("DRIVER")
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName("DRIVER");
                        newRole.setDescription("Only for driver");
                        return roleRepository.save(newRole);
                    });
        }

        User user = userMapper.toUser(request);
        user.setCreatedDate(LocalDate.now());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(userRole));
        String formatted = String.format("%09d", nextNumber);
        user.setUserNumber(formatted);
        user.setUsername(user.getUserNumber());
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request){
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User Not Found"));
        if(userRepository.existsByPhone(request.getPhone()))
            throw new CustomException(ErrorCode.USER_EXISTED);
        userMapper.updateUser(user, request);
        if(request.getPassword() != null)
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse myInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findById(name)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}