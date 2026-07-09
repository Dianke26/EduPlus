package com.example.eduplus.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String matricule;

    private String nom;
    private String prenom;

    @Column(unique = true)
    private String email;

    private String specialite;
}