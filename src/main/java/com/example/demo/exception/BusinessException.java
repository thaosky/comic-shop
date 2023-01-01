package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
}
