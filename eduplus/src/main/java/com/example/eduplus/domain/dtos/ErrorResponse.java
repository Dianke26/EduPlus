package com.example.eduplus.domain.dtos;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> details;

    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(int status, String error, String message, Map<String, String> details) {
        this(status, error, message);
        this.details = details;
    }
}