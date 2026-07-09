package com.example.eduplus.services;

import com.example.eduplus.domain.AppUser;
import com.example.eduplus.domain.RefreshToken;
import com.example.eduplus.domain.dtos.AuthResponse;
import com.example.eduplus.domain.dtos.LoginRequest;
import com.example.eduplus.domain.dtos.RefreshTokenRequest;
import com.example.eduplus.repository.RefreshTokenRepository;
import com.example.eduplus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${app.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public void register(AppUser request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email existe déjà");
        }

        AppUser user = new AppUser();
        user.setFullname(request.getFullname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        AppUser user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable")
                );

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }
    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.refreshToken())
                .orElseThrow(() ->
                        new RuntimeException("Refresh token invalide")
                );

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expiré");
        }

        AppUser user = refreshToken.getUser();
        String newAccessToken = jwtService.generateAccessToken(user);

        return new AuthResponse(
                newAccessToken,
                refreshToken.getToken(),
                "Bearer"
        );
    }
    @Transactional
    protected String createRefreshToken(AppUser user) {

        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                Instant.now().plusMillis(refreshTokenExpiration)
        );

        refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }

}