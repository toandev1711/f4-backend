package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.*;
//import com.example.f4backend.dto.reponse.UserResponse;
import com.example.f4backend.dto.request.*;
//import com.example.f4backend.entity.*;
import com.example.f4backend.enums.ErrorCode;
//import com.example.f4backend.service.DriverService;
import com.example.f4backend.repository.IdentifierCardRepository;
import com.example.f4backend.repository.VehicleTypeRepository;
import com.example.f4backend.service.CloudinaryService;
import com.example.f4backend.service.DriverService;
//import com.example.f4backend.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverController {
        private final DriverService driverService;

        @PostMapping
        public ApiResponse<DriverResponse> createDriver(@Valid @RequestBody DriverRequest request) {
                return ApiResponse.<DriverResponse>builder()
                                .code(ErrorCode.CREATE_DRIVER_SUCCESS.getCode())
                                .result(driverService.createDriver(request))
                                .message(ErrorCode.CREATE_DRIVER_SUCCESS.getMessage())
                                .build();
        }

        @PostMapping("/{driverId}/identifier-card")
        public ApiResponse<IdentifierCardResponse> createIdentifierCard(
                        @PathVariable String driverId,
                        @Valid @RequestBody IdentifierCardRequest request) {
                return ApiResponse.<IdentifierCardResponse>builder()
                                .code(ErrorCode.CREATE_IDENTIFIERCARD_SUCCESS.getCode())
                                .result(driverService.createIdentifierCard(driverId, request))
                                .message(ErrorCode.CREATE_IDENTIFIERCARD_SUCCESS.getMessage())
                                .build();
        }

        @PostMapping("/{driverId}/license-card")
        public ApiResponse<LicenseCarResponse> createLicenseCar(
                        @PathVariable String driverId,
                        @Valid @RequestBody LicenseCarRequest request) {
                return ApiResponse.<LicenseCarResponse>builder()
                                .code(ErrorCode.CREATE_LICENSECARD_SUCCESS.getCode())
                                .result(driverService.createLicenseCar(driverId, request))
                                .message(ErrorCode.CREATE_LICENSECARD_SUCCESS.getMessage())
                                .build();
        }

        @PostMapping("/{driverId}/vehicle-detail")
        public ApiResponse<VehicleDetailResponse> createVehicleDetail(
                        @PathVariable String driverId,
                        @Valid @RequestBody VehicleDetailRequest request) {
                return ApiResponse.<VehicleDetailResponse>builder()
                                .code(ErrorCode.CREATE_VEHICLEDETAIL_SUCCESS.getCode())
                                .result(driverService.createVehicleDetail(driverId, request))
                                .message(ErrorCode.CREATE_VEHICLEDETAIL_SUCCESS.getMessage())
                                .build();
        }

        // updateDriver and document

        @PutMapping("/{driverId}")
        public ApiResponse<DriverResponse> updateDriver(@PathVariable String driverId,
                        @RequestBody DriverUpdateRequest request) {
                return ApiResponse.<DriverResponse>builder()
                                .code(1000)
                                .result(driverService.updateDriver(driverId, request))
                                .message("Update successfully")
                                .build();
        }

        @PutMapping("/update-cccd/{driverId}")
        public ApiResponse<IdentifierCardResponse> updateIdentifierCard(
                        @PathVariable String driverId,
                        @RequestBody @Valid IdentifierCardRequest request) {
                return ApiResponse.<IdentifierCardResponse>builder()
                                .code(ErrorCode.UPDATE_DRIVER_SUCCESS.getCode())
                                .result(driverService.updateIdentifierCard(driverId, request))
                                .message(ErrorCode.UPDATE_DRIVER_SUCCESS.getMessage())
                                .build();
        }

        @PutMapping("/update-license-car/{driverId}/{licenseCarId}")
        public ApiResponse<LicenseCarResponse> updateLicenseCar(
                        @PathVariable String driverId,
                        @PathVariable String licenseCarId,
                        @RequestBody @Valid LicenseCarRequest request) {

                System.out.println("Received updateLicense request for ID: " + licenseCarId);
                System.out.println("Request data: " + request);

                return ApiResponse.<LicenseCarResponse>builder()
                                .code(ErrorCode.UPDATE_DRIVER_SUCCESS.getCode())
                                .result(driverService.updateLicenseCar(driverId, licenseCarId, request))
                                .message(ErrorCode.UPDATE_DRIVER_SUCCESS.getMessage())
                                .build();
        }

        @PutMapping("/update-vehicle/{driverId}/{vehicleId}")
        public ApiResponse<VehicleDetailResponse> updateVehicleDetail(
                        @PathVariable String driverId,
                        @PathVariable String vehicleId,
                        @RequestBody @Valid VehicleDetailRequest request) {

                return ApiResponse.<VehicleDetailResponse>builder()
                                .code(ErrorCode.UPDATE_DRIVER_SUCCESS.getCode())
                                .result(driverService.updateVehicleDetail(driverId, vehicleId, request))
                                .message(ErrorCode.UPDATE_DRIVER_SUCCESS.getMessage())
                                .build();
        }

        // getDocument and driverinfo
        // get IdntifierCar
        @GetMapping("/{driverId}/IdntifierCarInfo")
        public ApiResponse<IdentifierCardResponse> idntifierCarInfo(@PathVariable String driverId) {
                return ApiResponse.<IdentifierCardResponse>builder()
                                .result(driverService.getIdentifierCard(driverId))
                                .message("CCCD information")
                                .build();
        }

        // get licensecar
        @GetMapping("/{driverId}/LicenseCarInfo")
        public ApiResponse<List<LicenseCarResponse>> getLicenseCar(@PathVariable String driverId) {
                List<LicenseCarResponse> licenseCars = driverService.getLicenseCar(driverId);
                return ApiResponse.<List<LicenseCarResponse>>builder()
                                .result(licenseCars)
                                .message("License car information")
                                .build();
        }

        // get vehicle
        @GetMapping("/{driverId}/VehicleDetailInfo")
        public ApiResponse<List<VehicleDetailResponse>> getVehicleDetail(@PathVariable String driverId) {
                List<VehicleDetailResponse> vehicleDetails = driverService.getVehicleDetail(driverId);
                return ApiResponse.<List<VehicleDetailResponse>>builder()
                                .result(vehicleDetails)
                                .message("Vehicle detail information")
                                .build();
        }

        // get driver info
        @GetMapping("/info")
        public ApiResponse<DriverResponse> myInfo() {
                return ApiResponse.<DriverResponse>builder()
                                .result(driverService.driverInfo())
                                .message("Your information")
                                .build();
        }

        @GetMapping("/vehicle-type")
        public ApiResponse<List<VehicleTypeResponse>> vehicleType() {
                return ApiResponse.<List<VehicleTypeResponse>>builder()
                                .code(200)
                                .result(driverService.getVehicleType())
                                .message("VehicleType information")
                                .build();
        }

        @GetMapping("/driver-type")
        public ApiResponse<List<DriverTypeResponse>> driverType() {
                return ApiResponse.<List<DriverTypeResponse>>builder()
                                .code(200)
                                .result(driverService.getDriverTypes())
                                .message("DriverType information")
                                .build();
        }

        @GetMapping("/driver-status")
        public ApiResponse<List<DriverStatusResponse>> driverStatus() {
                return ApiResponse.<List<DriverStatusResponse>>builder()
                                .code(200)
                                .result(driverService.getDriverStatus())
                                .message("VehicleType information")
                                .build();
        }
}
