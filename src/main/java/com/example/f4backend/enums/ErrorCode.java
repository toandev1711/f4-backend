package com.example.f4backend.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEDGORIZED_EXCEPTION(9000,"Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_USERNAME(1002, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1003, "Password must be at least 10 characters", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1004, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_CREATED(200, "User created", HttpStatus.CREATED),
    USER_NOT_EXISTED(1005, "User not  existed", HttpStatus.NOT_FOUND),
    LOGIN_FAULT(1006, "Cannot login", HttpStatus.BAD_REQUEST),
    LOGIN_SUSSCESS(200, "Login susscessfully", HttpStatus.OK),
    GET_SUSSCESS(200, "Get susscessed", HttpStatus.OK),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    CREATE_USER_SUCCESS(1000, "User has been created", HttpStatus.OK),
    USER_EXISTED(1001, "User existed", HttpStatus.FOUND),
    EMAIL_ALREADY_EXISTS(1001, "User existed", HttpStatus.FOUND),
    PHONE_ALREADY_EXISTS(1001, "User existed", HttpStatus.FOUND)
    ;
    ErrorCode(int code, String message, HttpStatus httpStatus){
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