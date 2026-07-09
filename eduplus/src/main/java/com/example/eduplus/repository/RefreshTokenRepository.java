package com.example.eduplus.repository;

import com.example.eduplus.domain.AppUser;
import com.example.eduplus.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(AppUser user);
}

