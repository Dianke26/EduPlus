package com.example.eduplus.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String nom;
    private int credits;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;
}