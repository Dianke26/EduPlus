package com.example.eduplus.domain.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoursRequest {
    @NotBlank private String code;
    @NotBlank private String nom;
    @Min(1) private int credits;
    @NotNull private Long enseignantId;
}