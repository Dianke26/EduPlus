package com.example.eduplus.repository;

import com.example.eduplus.domain.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    boolean existsByEtudiantIdAndCoursId(Long etudiantId, Long coursId);
}