package com.example.patient.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseError {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
