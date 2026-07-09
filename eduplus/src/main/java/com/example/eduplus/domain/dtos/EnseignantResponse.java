package com.example.eduplus.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnseignantResponse {
    private Long id;
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String specialite;
}