package com.example.eduplus.domain.dtos;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) { }
