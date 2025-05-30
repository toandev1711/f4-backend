package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.*;
import com.example.f4backend.dto.request.*;
import com.example.f4backend.entity.*;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.exception.CustomException;
import com.example.f4backend.mapper.DriverMapper;
import com.example.f4backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        VehicleTypeRepository vehicleTypeRepository;
        DriverStatusRepository driverStatusRepository;
        DriverTypeRepository driverTypeRepository;
        DriverMapper driverMapper;
        PasswordEncoder passwordEncoder;
        RoleRepository roleRepository;


        @Transactional
        public DriverResponse createDriver(DriverRequest request) {
                if(driverRepository.existsByPhone(request.getPhone()))
                        throw new CustomException(ErrorCode.USER_EXISTED);
                DriverStatus driverStatus = driverStatusRepository.findById(3)
                        .orElseThrow(() -> new RuntimeException("DriverStatus not found"));
                DriverType driverType = driverTypeRepository.findById(request.getDriverTypeId())
                        .orElseThrow(() -> new RuntimeException("DriverType not found"));

                // Map and save Driver
                Driver driver = driverMapper.toDriver(request , driverStatus ,driverType);
                driver.setPassword(passwordEncoder.encode(request.getPassword()));
                driver.setDriverType(driverType);
                driver.setDriverStatus(driverStatus);

                Role driverRole = new Role();
                driverRole = roleRepository.findByName("DRIVER")
                        .orElseGet(() -> {
                                Role newRole = new Role();
                                newRole.setName("DRIVER");
                                newRole.setDescription("Only for driver");
                                return roleRepository.save(newRole);
                        });
                driver.setRoles(Set.of(driverRole));

                return driverMapper.toDriverResponse(driverRepository.save(driver));
        }

        @Transactional
        public IdentifierCardResponse createIdentifierCard(String driverId, IdentifierCardRequest request) {
                // Check if driver exists
                if (!driverRepository.existsByDriverId(driverId))
                        throw new CustomException(ErrorCode.USER_NOT_EXISTED);

                Driver driver = driverRepository.findById(driverId)
                                .orElseThrow(() -> new RuntimeException("Driver not found"));

                DocumentStatus documentStatus = documentStatusRepository.findById(1)
                                .orElseThrow(() -> new RuntimeException("DocumentStatus not found"));

                // Map and save IdentifierCard
                IdentifierCard identifierCard = driverMapper.toIdentifierCard(request, driver, documentStatus);
                identifierCard.setDriver(driver);
                identifierCard.setStatus(documentStatus);
                return driverMapper.toIdentifierCardResponse(identifierCardRepository.save(identifierCard));
        }

        @Transactional
        public LicenseCarResponse createLicenseCar(String driverId, LicenseCarRequest request) {
                // Check if driver exists
                if (!driverRepository.existsByDriverId(driverId))
                        throw new CustomException(ErrorCode.USER_NOT_EXISTED);
                if (licenseCarRepository.existsByLicenseNumber(request.getLicenseNumber()))
                        throw new CustomException(ErrorCode.LICENSENUMBER_ALREADY_EXISTS);
                Driver driver = driverRepository.findById(driverId)
                                .orElseThrow(() -> new RuntimeException("Driver not found"));
                DocumentStatus documentStatus = documentStatusRepository.findById(1)
                                .orElseThrow(() -> new RuntimeException("DocumentStatus not found"));
                // Map and save LicenseCar
                LicenseCar licenseCar = driverMapper.toLicenseCar(request, driver, documentStatus);
                licenseCar.setDriver(driver);
                licenseCar.setStatus(documentStatus);
                return driverMapper.toLicenseCarResponse(licenseCarRepository.save(licenseCar));
        }

        @Transactional
        public VehicleDetailResponse createVehicleDetail(String driverId, VehicleDetailRequest request) {
                // Check if driver exists
                if (!driverRepository.existsByDriverId(driverId))
                        throw new CustomException(ErrorCode.USER_NOT_EXISTED);
                if (vehicleDetailRepository.existsByLicensePlateNumber(request.getLicensePlateNumber()))
                        throw new CustomException(ErrorCode.LICENSEPLATENUMBER_ALREADY_EXISTS);
                Driver driver = driverRepository.findById(driverId)
                                .orElseThrow(() -> new RuntimeException("Driver not found"));
                DocumentStatus documentStatus = documentStatusRepository.findById(1)
                                .orElseThrow(() -> new RuntimeException("DocumentStatus not found"));
                VehicleType vehicleType = vehicleTypeRepository.findById(request.getVehicleTypeId())
                                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

                // Map and save VehicleDetail
                VehicleDetail vehicleDetail = driverMapper.toVehicleDetail(request, driver, documentStatus);
                vehicleDetail.setDriver(driver);
                vehicleDetail.setStatus(documentStatus);
                vehicleDetail.setVehicleType(vehicleType);
                return driverMapper.toVehicleDetailResponse(vehicleDetailRepository.save(vehicleDetail));
        }

        // @Transactional
        // public UserResponse updateDriverInfo(String userId, DriverInfoRequest
        // request) {
        // User user = userRepository.findById(userId)
        // .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
        // driverInfoMapper.updateUserFromDto(request, user);
        // User updatedUser = userRepository.save(user);
        // return driverInfoMapper.toResponseDto(updatedUser);
        // }

//        public IdentifierCardResponse updateIdentifierCard(IdentifierCardRequest request) {
//                String userId = SecurityContextHolder.getContext().getAuthentication().getName();
//
////                Driver driverid = driverRepository.findByUserId(userId)
////                                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
//
//                Driver driver = driverRepository.findById(driverid.getDriverId())
//                                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
//
//                DocumentStatus documentStatus = documentStatusRepository.findById(1)
//                                .orElseThrow(() -> new CustomException(ErrorCode.STATUS_NOT_FOUND));
//
//                IdentifierCard card = identifierCardRepository.findByDriverDriverId(driverid.getDriverId())
//                                .orElseThrow(() -> new CustomException(ErrorCode.DOCUMENT_NOT_FOUND));
//
//                driverMapper.updateIdentifierCardFromDto(request, card);
//                card.setDriver(driver);
//                card.setStatus(documentStatus);
//
//                IdentifierCard updatedCard = identifierCardRepository.save(card);
//                return driverMapper.toIdentifierCardResponse(updatedCard);
//        }

        //        public LicenseCarResponse updateLicenseCar(String licenseCarId, LicenseCarRequest request) {
        //                String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        //
        //                Driver driverid = driverRepository.findByUserId(userId)
        //                                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
        //
        //                Driver driver = driverRepository.findById(driverid.getDriverId())
        //                                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
        //
        //                DocumentStatus documentStatus = documentStatusRepository.findById(1)
        //                                .orElseThrow(() -> new CustomException(ErrorCode.STATUS_NOT_FOUND));
        //
        //                LicenseCar license = licenseCarRepository
        //                                .findByLicenseCarIdAndDriverDriverId(licenseCarId, driverid.getDriverId())
        //                                .orElseThrow(() -> new CustomException(ErrorCode.DOCUMENT_NOT_FOUND));
        //                driverMapper.updateLicenseCarFromDto(request, license);
        //
        //                license.setDriver(driver);
        //                license.setStatus(documentStatus);
        //
        //                LicenseCar updatedLicenseCar = licenseCarRepository.save(license);
        //                return driverMapper.toLicenseCarResponse(updatedLicenseCar);
        //        }
        //
        //        public VehicleDetailResponse updateVehicleDetail(String vehicleId, VehicleDetailRequest request) {
        //                String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        //
        //                Driver driverid = driverRepository.findByUserId(userId)
        //                                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
        //
        //                Driver driver = driverRepository.findById(driverid.getDriverId())
        //                                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
        //
        //                DocumentStatus documentStatus = documentStatusRepository.findById(1)
        //                                .orElseThrow(() -> new CustomException(ErrorCode.STATUS_NOT_FOUND));
        //
        //                VehicleDetail vehicle = vehicleDetailRepository
        //                                .findByVehicleIdAndDriverDriverId(vehicleId, driverid.getDriverId())
        //                                .orElseThrow(() -> new CustomException(ErrorCode.DOCUMENT_NOT_FOUND));
        //
        //                VehicleType vehicleType = vehicleTypeRepository.findById(request.getVehicleTypeId())
        //                                .orElseThrow(() -> new CustomException(ErrorCode.DOCUMENT_NOT_FOUND));
        //
        //                driverMapper.updateVehicleDetailFromDto(request, vehicle);
        //
        //                vehicle.setDriver(driver);
        //                vehicle.setStatus(documentStatus);
        //                vehicle.setVehicleType(vehicleType);
        //                VehicleDetail updatedVehicleDetail = vehicleDetailRepository.save(vehicle);
        //                return driverMapper.toVehicleDetailResponse(updatedVehicleDetail);
        //        }
        //
        //        public IdentifierCardResponse getIdentifierCard() {
        //                String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        //
        //                Driver driver = driverRepository.findByUserId(userId)
        //                                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
        //
        //                IdentifierCard identifierCard = identifierCardRepository.findByDriverDriverId(driver.getDriverId())
        //                                .orElseThrow(() -> new CustomException(ErrorCode.DOCUMENT_NOT_FOUND));
        //                return driverMapper.toIdentifierCardResponse(identifierCard);
        //        }
        //
        //        public List<LicenseCarResponse> getLicenseCar() {
        //                String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        //
        //                Driver driver = driverRepository.findByUserId(userId)
        //                                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
        //
        //                List<LicenseCar> licenseCars = licenseCarRepository.findByDriverDriverId(driver.getDriverId());
        //                if (licenseCars.isEmpty()) {
        //                        throw new CustomException(ErrorCode.DOCUMENT_NOT_FOUND);
        //                }
        //                return licenseCars.stream()
        //                                .map(driverMapper::toLicenseCarResponse)
        //                                .collect(Collectors.toList());
        //        }
        //
        //        public List<VehicleDetailResponse> getVehicleDetail() {
        //                String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        //
        //                Driver driver = driverRepository.findByUserId(userId)
        //                                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));
        //
        //                List<VehicleDetail> vehicleDetails = vehicleDetailRepository.findByDriverDriverId(driver.getDriverId());
        //                if (vehicleDetails.isEmpty()) {
        //                        throw new CustomException(ErrorCode.DOCUMENT_NOT_FOUND);
        //                }
        //                return vehicleDetails.stream()
        //                                .map(driverMapper::toVehicleDetailResponse)
        //                                .collect(Collectors.toList());
        //        }

        public List<VehicleTypeResponse> getVehicleType() {
                List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
                if (vehicleTypeList.isEmpty()) {
                        throw new CustomException(ErrorCode.DOCUMENT_NOT_FOUND);
                }
                return vehicleTypeList.stream()
                        .map(driverMapper::VehicleTypeResponse)
                        .collect(Collectors.toList());
        }

        public List<DriverStatusResponse> getDriverStatus() {
                List<DriverStatus> driverStatusList = driverStatusRepository.findAll();
                if (driverStatusList.isEmpty()) {
                        throw new CustomException(ErrorCode.DOCUMENT_NOT_FOUND);
                }
                return driverStatusList.stream()
                        .map(driverMapper::toDriverStatusResponse)
                        .collect(Collectors.toList());
        }

        public List<DriverTypeResponse> getDriverTypes() {
                List<DriverType> driverTypesList = driverTypeRepository.findAll();
                if (driverTypesList.isEmpty()) {
                        throw new CustomException(ErrorCode.DOCUMENT_NOT_FOUND);
                }
                return driverTypesList.stream()
                        .map(driverMapper::toDriverTypeResponse)
                        .collect(Collectors.toList());
        }
}