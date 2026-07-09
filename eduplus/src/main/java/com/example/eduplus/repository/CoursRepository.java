package com.example.eduplus.repository;

import com.example.eduplus.domain.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    boolean existsByCode(String code);
}