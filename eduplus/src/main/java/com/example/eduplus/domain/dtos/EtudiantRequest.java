package com.example.eduplus.domain.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class EtudiantRequest {
    @NotBlank private String matricule;
    @NotBlank private String nom;
    @NotBlank private String prenom;
    @Email @NotBlank private String email;
    @Past private LocalDate dateNaissance;
    @NotBlank private String classe;
}