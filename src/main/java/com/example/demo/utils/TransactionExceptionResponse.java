package com.example.demo.utils;

import java.time.LocalDateTime;

public class TransactionExceptionResponse {

    private int errorCode;

    private String message;

    private LocalDateTime timestamp;

    private String details;

    public TransactionExceptionResponse(int errorCode, String message, String details, LocalDateTime timestamp) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
    }

    @Override
    public String toString() {
        return "TransactionExceptionResponse{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", details='" + details + '\'' +
                '}';
    }
}