package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.*;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.service.DriverService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    private final DriverService driverService;
    @GetMapping("/drivers")
    public Page<DriverResponse> getDrivers(
            @RequestParam(defaultValue = "") String searchTerm,
            @RequestParam(defaultValue = "All") String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size) {

        return driverService.getDrivers(searchTerm, status, page, size);
    }
    @PutMapping("/update-cccd/{driverId}/{statusId}")
    public ApiResponse<IdentifierCardResponse> updateIdentifierCardStatus(
            @PathVariable String driverId,
            @PathVariable String statusId) {
        return ApiResponse.<IdentifierCardResponse>builder()
                .code(ErrorCode.UPDATE_DOCUMENT_SUCCESS.getCode())
                .result(driverService.updateIdentifierCardStatus(driverId, statusId))
                .message(ErrorCode.UPDATE_DOCUMENT_SUCCESS.getMessage())
                .build();
    }
    @PutMapping("/update-vehicle/{driverId}/{vehicleId}/{statusId}")
    public ApiResponse<VehicleDetailResponse> updateVehicleDetailStatus(
            @PathVariable String driverId,
            @PathVariable String vehicleId,
            @PathVariable String statusId) {

        return ApiResponse.<VehicleDetailResponse>builder()
                .code(ErrorCode.UPDATE_DOCUMENT_SUCCESS.getCode())
                .result(driverService.updateVehilceCardStatus(driverId, vehicleId, statusId))
                .message(ErrorCode.UPDATE_DOCUMENT_SUCCESS.getMessage())
                .build();
    }
    @PutMapping("/update-license-car/{driverId}/{licenseCarId}/{statusId}")
    public ApiResponse<LicenseCarResponse> updateLicenseCarStatus(
            @PathVariable String driverId,
            @PathVariable String licenseCarId,
            @PathVariable String statusId) {
        return ApiResponse.<LicenseCarResponse>builder()
                .code(ErrorCode.UPDATE_DOCUMENT_SUCCESS.getCode())
                .result(driverService.updateLicenseCardStatus(driverId, licenseCarId, statusId))
                .message(ErrorCode.UPDATE_DOCUMENT_SUCCESS.getMessage())
                .build();
    }
    @PutMapping("/block/{driverId}/{isBlocked}")
    public ApiResponse<DriverResponse> blockDriver(
            @PathVariable String driverId,
            @PathVariable String isBlocked) {
        return ApiResponse.<DriverResponse>builder()
                .code(ErrorCode.UPDATE_DRIVER_SUCCESS.getCode())
                .result(driverService.blockDriver(driverId,isBlocked))
                .message(ErrorCode.UPDATE_DRIVER_SUCCESS.getMessage())
                .build();
    }
}
