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
package com.example.f4backend.service;

import com.example.f4backend.dto.request.*;
import com.example.f4backend.entity.*;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.exception.CustomException;
import com.example.f4backend.mapper.DriverMapper;
import com.example.f4backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverService {

        UserRepository userRepository;
        DriverRepository driverRepository;
        IdentifierCardRepository identifierCardRepository;
        LicenseCarRepository licenseCarRepository;
        VehicleDetailRepository vehicleDetailRepository;
        DocumentStatusRepository documentStatusRepository;

        DriverMapper.DriverInfoMapper driverInfoMapper;
        DriverMapper.IdentifierCardMapper identifierCardMapper;
        DriverMapper.LicenseCarMapper licenseCarMapper;
        DriverMapper.VehicleDetailMapper vehicleDetailMapper;
        DriverMapper.UserMapper userMapper;

        @Transactional
        public void updateDriverInfo(String userId, DriverInfoRequest request) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
                driverInfoMapper.updateUserFromDto(request, user);
                userRepository.save(user);
        }


        public void updateIdentifierCard(String driverId, IdentifierCardRequest request) {
                Driver driver = driverRepository.findById(driverId)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));

                DocumentStatus documentStatus = documentStatusRepository.findById(1)
                        .orElseThrow(() -> new CustomException(ErrorCode.STATUS_NOT_FOUND));

                IdentifierCard card = identifierCardRepository.findByDriverDriverId(driverId)
                        .orElse(new IdentifierCard());

                card.setDriver(driver);
                card.setStatus(documentStatus);
                identifierCardMapper.updateIdentifierCardFromDto(request, card);

                identifierCardRepository.save(card);
        }
        public void updateLicenseCar(String driverId, LicenseCarRequest request) {
                Driver driver = driverRepository.findById(driverId)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));

                DocumentStatus documentStatus = documentStatusRepository.findById(1)
                        .orElseThrow(() -> new CustomException(ErrorCode.STATUS_NOT_FOUND));

                LicenseCar license = licenseCarRepository.findByDriverDriverId(driverId)
                        .orElse(new LicenseCar());

                license.setDriver(driver);
                license.setStatus(documentStatus);
                licenseCarMapper.updateLicenseCarFromDto(request, license);

                licenseCarRepository.save(license);
        }

        public VehicleDetail updateVehicleDetail(String driverId, VehicleDetailRequest request) {
                Driver driver = driverRepository.findById(driverId)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));

                DocumentStatus documentStatus = documentStatusRepository.findById(1)
                        .orElseThrow(() -> new CustomException(ErrorCode.STATUS_NOT_FOUND));

                VehicleDetail vehicle = vehicleDetailRepository.findByDriverDriverId(driverId)
                        .orElse(new VehicleDetail());

                vehicle.setDriver(driver);
                vehicle.setStatus(documentStatus);
                vehicleDetailMapper.updateVehicleDetailFromDto(request, vehicle);

                return vehicleDetailRepository.save(vehicle);
        }
}