package com.example.eduplus.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InscriptionRequest {
    @NotNull private Long etudiantId;
    @NotNull private Long coursId;
}