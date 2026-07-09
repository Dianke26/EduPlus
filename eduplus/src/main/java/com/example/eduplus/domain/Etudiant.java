package com.example.eduplus.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String matricule;

    private String nom;
    private String prenom;

    @Column(unique = true)
    private String email;

    private LocalDate dateNaissance;
    private String classe;
    private String photoUrl;
    private String documentUrl;
}