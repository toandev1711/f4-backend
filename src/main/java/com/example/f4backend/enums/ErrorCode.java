package com.example.f4backend.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEDGORIZED_EXCEPTION(9000, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_USERNAME(1002, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1003, "Password must be at least 10 characters", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1004, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_CREATED(200, "User created", HttpStatus.CREATED),
    USER_NOT_EXISTED(1005, "User not  existed", HttpStatus.NOT_FOUND),
    LOGIN_FAULT(1006, "Cannot login", HttpStatus.BAD_REQUEST),
    LOGIN_SUSSCESS(200, "Login susscessfully", HttpStatus.OK),
    GET_SUSSCESS(200, "Get susscessed", HttpStatus.OK),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    CREATE_ORDER_SUCCESS(1000, "Order created", HttpStatus.CREATED),
    CREATE_USER_SUCCESS(1000, "User has been created", HttpStatus.OK),
    CREATE_DRIVER_SUCCESS(1000, "Driver has been created", HttpStatus.OK),
    CREATE_IDENTIFIERCARD_SUCCESS(1000, "IndentifierCard has been created", HttpStatus.OK),
    CREATE_LICENSECARD_SUCCESS(1000, "Licensecard has been created", HttpStatus.OK),
    CREATE_VEHICLEDETAIL_SUCCESS(1000, "VehicleDetail has been created", HttpStatus.OK),
    CREATE_ORDERDETAIL_SUCCESS(1001, "Order detail has been created successfully", HttpStatus.OK),
    CREATE_COORDINATES_SUCCESS(1000, "Coordinates created successfully", HttpStatus.OK),
    USER_EXISTED(1001, "User existed", HttpStatus.FOUND),
    EMAIL_ALREADY_EXISTS(1001, "Email existed", HttpStatus.FOUND),
    PHONE_ALREADY_EXISTS(1001, "PhoneNumber existed", HttpStatus.FOUND),
    COORDINATES_ALREADY_EXISTS(1001, "Coordinates existed", HttpStatus.FOUND),
    DELIVERY_DETAIL_NOT_EXISTS(1001, "Delivery detail not exist", HttpStatus.FOUND),
    LICENSEPLATENUMBER_ALREADY_EXISTS(1001, "VehicleDetail - License Plate Number existed", HttpStatus.FOUND),
    LICENSENUMBER_ALREADY_EXISTS(1001, "LicenseCard - License Number existed", HttpStatus.FOUND),
    DOCUMENT_NOT_FOUND(1001, "No Document", HttpStatus.FOUND),
    STATUS_NOT_FOUND(1001, "No Status Document", HttpStatus.FOUND),
    UPDATE_DRIVER_SUCCESS(200, "Update driver successful", HttpStatus.OK),
    WALLET_NOT_FOUND(1001, "Wallet not found", HttpStatus.FOUND),
    TRANSACTION_TYPE_NOT_FOUND(1001, "Transaction type not found", HttpStatus.FOUND),
    TRANSACTION_STATUS_NOT_FOUND(1001, "Transaction status not found", HttpStatus.FOUND),
    INSUFFICIENT_BALANCE(1001, "Insufficient balance for withdrawal", HttpStatus.FOUND),
    INVALID_AMOUNT(1001, "Transaction amount must be greater than 0" , HttpStatus.FOUND);
    ;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int code;
    private String message;
    private HttpStatus httpStatus;
}