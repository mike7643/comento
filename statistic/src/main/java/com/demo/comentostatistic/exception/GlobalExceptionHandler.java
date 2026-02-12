package com.demo.comentostatistic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidStatRequestException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRequest(InvalidStatRequestException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "잘못된 요청");
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
