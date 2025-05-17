package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.DriverResponse;
import com.example.f4backend.dto.reponse.IdentifierCardResponse;
import com.example.f4backend.dto.reponse.LicenseCarResponse;
import com.example.f4backend.dto.reponse.VehicleDetailResponse;
import com.example.f4backend.dto.request.*;
import com.example.f4backend.entity.*;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.exception.DriverException;
import com.example.f4backend.mapper.DriverMapper;
import com.example.f4backend.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverService {
    UserRepository userRepository;
    DriverRepository driverRepository;
    IdentifierCardRepository identifierCardRepository;
    VehicleDetailRepository vehicleDetailRepository;
    LicenseCarRepository licenseCarRepository;
    DocumentStatusRepository documentStatusRepository;
    DriverMapper driverMapper;
    PasswordEncoder passwordEncoder;

    public DriverResponse createDriver(DriverRequest request) {
        if(!userRepository.existsById(request.getUserId()))
            throw new DriverException(ErrorCode.USER_NOT_EXISTED);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Map and save Driver
        Driver driver = driverMapper.toDriver(request);
        driver.setUser(user);
        return driverMapper.toDriverResponse(driverRepository.save(driver));
    }

    public IdentifierCardResponse createIdentifierCard(String driverId, IdentifierCardRequest request) {
        // Check if driver exists
        if(!driverRepository.existsByDriverId(driverId))
            throw new DriverException(ErrorCode.USER_NOT_EXISTED);

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        DocumentStatus documentStatus = documentStatusRepository.findById(1).
                orElseThrow(() -> new RuntimeException("DocumentStatus not found"));

        // Map and save IdentifierCard
        IdentifierCard identifierCard = driverMapper.toIdentifierCard(request, driver,documentStatus);
        identifierCard.setDriver(driver);
        identifierCard.setStatus(documentStatus);
        return driverMapper.toIdentifierCardResponse(identifierCardRepository.save(identifierCard));
    }

    public LicenseCarResponse createLicenseCar(String driverId, LicenseCarRequest request) {
        // Check if driver exists
        if(!driverRepository.existsByDriverId(driverId))
            throw new DriverException(ErrorCode.USER_NOT_EXISTED);
        if(licenseCarRepository.existsByLicenseNumber(request.getLicenseNumber()))
            throw new DriverException(ErrorCode.LICENSENUMBER_ALREADY_EXISTS);
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        DocumentStatus documentStatus = documentStatusRepository.findById(1).
                orElseThrow(() -> new RuntimeException("DocumentStatus not found"));
        // Map and save LicenseCar
        LicenseCar licenseCar = driverMapper.toLicenseCar(request, driver ,documentStatus);
        licenseCar.setDriver(driver);
        licenseCar.setStatus(documentStatus);
        return driverMapper.toLicenseCarResponse(licenseCarRepository.save(licenseCar));
    }


    public VehicleDetailResponse createVehicleDetail(String driverId, VehicleDetailRequest request) {
        // Check if driver exists
        if(!driverRepository.existsByDriverId(driverId))
            throw new DriverException(ErrorCode.USER_NOT_EXISTED);
        if(vehicleDetailRepository.existsByLicensePlateNumber(request.getLicensePlateNumber()))
            throw new DriverException(ErrorCode.LICENSEPLATENUMBER_ALREADY_EXISTS);
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        DocumentStatus documentStatus = documentStatusRepository.findById(1).
                orElseThrow(() -> new RuntimeException("DocumentStatus not found"));

        // Map and save VehicleDetail
        VehicleDetail vehicleDetail = driverMapper.toVehicleDetail(request, driver ,documentStatus);
        vehicleDetail.setDriver(driver);
        vehicleDetail.setStatus(documentStatus);
        return driverMapper.toVehicleDetailResponse(vehicleDetailRepository.save(vehicleDetail));
    }
}
