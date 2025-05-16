//package com.example.f4backend.controller;
//
//
//import com.example.f4backend.dto.reponse.ApiResponse;
//import com.example.f4backend.dto.request.DriverRegistrationRequest;
//import com.example.f4backend.entity.Driver;
//import com.example.f4backend.enums.ErrorCode;
////import com.example.f4backend.service.DriverService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/drivers")
//@RequiredArgsConstructor
//public class DriverController {
//    private final DriverService driverService;
//
//    @PostMapping("/register")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ApiResponse> registerDriver(@RequestBody DriverRegistrationRequest request) {
//        try {
//            Driver driver = driverService.createDriver(request);
//            ApiResponse response = new ApiResponse();
//            response.setCode(ErrorCode.CREATE_USER_SUCCESS.getCode());
//            response.setResult(driver.getDriverId());
//            response.setMessage(ErrorCode.CREATE_USER_SUCCESS.getMessage());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            ApiResponse response = new ApiResponse();
//            response.setCode(ErrorCode.UNCATEDGORIZED_EXCEPTION.getCode());
//            response.setMessage(e.getMessage());
//            return ResponseEntity.status(ErrorCode.UNCATEDGORIZED_EXCEPTION.getHttpStatus()).body(response);
//        }
//    }
//}
