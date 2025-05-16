//package com.example.f4backend.service;
//
//import com.example.f4backend.dto.request.DriverRegistrationRequest;
//import com.example.f4backend.entity.*;
//import com.example.f4backend.enums.ErrorCode;
//import com.example.f4backend.mapper.DriverMapper;
//import com.example.f4backend.repository.*;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class DriverService {
//    private final UserRepository userRepository;
//    private final DriverRepository driverRepository;
//    private final IdentifierCardRepository identifierCardRepository;
//    private final VehicleDetailRepository vehicleDetailRepository;
//    private final LicenseCarRepository licenseCarRepository;
//    private final DocumentStatusRepository documentStatusRepository;
//    private final DriverMapper driverMapper;
//    private final PasswordEncoder passwordEncoder;
//
//    @Transactional
//    public Driver createDriver(DriverRegistrationRequest request) {
//        // Check if email already exists
//        userRepository.findByEmail(request.getEmail())
//                .ifPresent(user -> {
//                    throw new RuntimeException(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
//                });
//
//        // Map and save User
//        User user = driverMapper.toUser(request);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user = userRepository.save(user);
//
//        // Map and save Driver
//        Driver driver = driverMapper.toDriver(request, user);
//        driver = driverRepository.save(driver);
//
//        // Map and save IdentifierCard
//        IdentifierCard identifierCard = driverMapper.toIdentifierCard(request.getIdentifierCard(), driver);
//        identifierCardRepository.save(identifierCard);
//        driver.setIdentifierCard(identifierCard);
//
//        // Map and save VehicleDetail
//        if (request.getVehicle() != null) {
//            VehicleDetail vehicleDetail = driverMapper.toVehicleDetail(request.getVehicle(), driver);
//            vehicleDetailRepository.save(vehicleDetail);
//        }
//
//        // Map and save LicenseCar
//        if (request.getLicense() != null) {
//            LicenseCar licenseCar = driverMapper.toLicenseCar(request.getLicense(), driver);
//            licenseCarRepository.save(licenseCar);
//        }
//
//        return driverRepository.save(driver);
//    }
//}
