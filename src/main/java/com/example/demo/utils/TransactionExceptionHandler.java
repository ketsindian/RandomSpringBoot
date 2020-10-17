package com.example.demo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class TransactionExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<TransactionExceptionResponse> resourceNotFound(ResourceNotFoundException e, WebRequest request) {
        TransactionExceptionResponse transactionException=new TransactionExceptionResponse(HttpStatus.NOT_FOUND.value(),"RESOURCE_NOT_FOUND",e.getMessage(),LocalDateTime.now());
        return new ResponseEntity<>(transactionException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<TransactionExceptionResponse> serverError(Exception e, WebRequest request) {
        TransactionExceptionResponse transactionException=new TransactionExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"INTERNAL_SERVER_ERROR",e.getMessage(),LocalDateTime.now());
        return new ResponseEntity<>(transactionException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TransactionException.class)
    public final ResponseEntity<TransactionExceptionResponse> trasactionError(TransactionException e, WebRequest request) {
        TransactionExceptionResponse transactionException = new TransactionExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"INTERNAL_SERVER_ERROR",e.getMessage(),LocalDateTime.now());
        return new ResponseEntity(transactionException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}