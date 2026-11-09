package com.example.patient.exceptions;

import com.example.patient.models.dto.response.ApiResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlers {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Dữ liệu từ người dùng nhập sai", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(e->errors.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(HttpNotFoundException.class)
    public ResponseEntity<?> handleHttpNotFoundException(HttpNotFoundException ex) {
        log.warn("Warn {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(InvalidAgeException.class)
    public ResponseEntity<?> handleInvalidAgeException(InvalidAgeException ex) {
        log.warn("Cảnh báo tuổi không hợp lệ: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Hệ thống đang gặp sự cố , đợi trong giây lát{}",ex.getMessage());
        ApiResponseError apiResponseError = ApiResponseError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Loi server")
                .message("Hệ thống đang gặp sự cố, vui lòng thử lại sau.")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseError);
    }


}
