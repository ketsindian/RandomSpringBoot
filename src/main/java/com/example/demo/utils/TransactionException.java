package com.example.demo.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TransactionException extends RuntimeException {
    public TransactionException(String exception) {
        super(exception);
    }
}
