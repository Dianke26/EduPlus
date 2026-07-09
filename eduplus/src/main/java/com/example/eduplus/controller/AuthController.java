package com.example.eduplus.controller;


import com.example.eduplus.domain.AppUser;
import com.example.eduplus.domain.dtos.AuthResponse;
import com.example.eduplus.domain.dtos.LoginRequest;
import com.example.eduplus.domain.dtos.RefreshTokenRequest;
import com.example.eduplus.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody AppUser request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        return authService.refreshToken(request);
    }


}
