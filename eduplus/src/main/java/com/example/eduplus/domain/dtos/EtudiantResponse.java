package com.example.eduplus.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EtudiantResponse {
    private Long id;
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;
    private String classe;
    private String photoUrl;
    private String documentUrl;
}