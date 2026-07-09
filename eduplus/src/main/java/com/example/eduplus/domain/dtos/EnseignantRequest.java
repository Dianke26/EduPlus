package com.example.eduplus.domain.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnseignantRequest {
    @NotBlank private String matricule;
    @NotBlank private String nom;
    @NotBlank private String prenom;
    @Email @NotBlank private String email;
    @NotBlank private String specialite;
}