package com.example.eduplus.repository;

import com.example.eduplus.domain.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByMatricule(String matricule);
    boolean existsByEmail(String email);
    boolean existsByMatricule(String matricule);
}