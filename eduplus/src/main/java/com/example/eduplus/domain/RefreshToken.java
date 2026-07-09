package com.example.eduplus.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.Instant;

@Entity
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @OneToOne
    private AppUser user;
}

