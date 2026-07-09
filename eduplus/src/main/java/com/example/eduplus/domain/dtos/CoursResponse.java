package com.example.eduplus.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoursResponse {
    private Long id;
    private String code;
    private String nom;
    private int credits;
    private String enseignantNomComplet;
}