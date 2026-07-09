package com.example.eduplus.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class InscriptionResponse {
    private Long id;
    private String etudiantNomComplet;
    private String coursNom;
    private LocalDate dateInscription;
}