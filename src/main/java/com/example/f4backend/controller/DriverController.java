package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.*;
//import com.example.f4backend.dto.reponse.UserResponse;
import com.example.f4backend.dto.request.*;
//import com.example.f4backend.entity.*;
import com.example.f4backend.enums.ErrorCode;
//import com.example.f4backend.service.DriverService;
import com.example.f4backend.repository.IdentifierCardRepository;
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
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverController {
    private final DriverService driverService;
    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload-image")
    public ApiResponse<String> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        String uploadedImage = cloudinaryService.getImageUrlAfterUpload(image);

        return ApiResponse.<String>builder()
                .code(ErrorCode.CREATE_IDENTIFIERCARD_SUCCESS.getCode())
                .result(uploadedImage)
                .message("Upload successfull")
                .build();
    }

    @PostMapping("/update-image")
    public ApiResponse<String> updateImage(
            @RequestParam("newImage") MultipartFile newImage,
            @RequestParam("oldUrl") String oldUrl
    ) throws IOException {
        String newUrl = cloudinaryService.updateImage(newImage, oldUrl);
        return ApiResponse.<String>builder()
                .code(ErrorCode.CREATE_IDENTIFIERCARD_SUCCESS.getCode())
                .message("Update successful")
                .result(newUrl)
                .build();
    }


    @PostMapping("")
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

    // updateDriver

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

        return ApiResponse.<LicenseCarResponse>builder()
                .code(ErrorCode.UPDATE_DRIVER_SUCCESS.getCode())
                .result(driverService.updateLicenseCar(licenseCarId,driverId, request))
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
                .result(driverService.updateVehicleDetail(vehicleId,driverId, request))
                .message(ErrorCode.UPDATE_DRIVER_SUCCESS.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
