package com.example.eduplus.repository;

import com.example.eduplus.domain.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    Optional<Enseignant> findByMatricule(String matricule);
    boolean existsByEmail(String email);
}