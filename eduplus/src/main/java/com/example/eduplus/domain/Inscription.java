package com.example.eduplus.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"etudiant_id", "cours_id"}))
@Getter
@Setter
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;

    private LocalDate dateInscription;
}