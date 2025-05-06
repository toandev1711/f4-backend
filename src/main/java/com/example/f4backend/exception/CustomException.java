package com.example.f4backend.exception;

import com.example.f4backend.enums.ErrorCode;

public class CustomException extends RuntimeException{
    private ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
