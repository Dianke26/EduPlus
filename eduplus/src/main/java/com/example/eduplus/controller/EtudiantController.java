package com.example.eduplus.controller;

import com.example.eduplus.domain.dtos.EtudiantRequest;
import com.example.eduplus.domain.dtos.EtudiantResponse;
import com.example.eduplus.services.EtudiantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

    private final EtudiantService etudiantService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ENSEIGNANT')")
    public ResponseEntity<Page<EtudiantResponse>> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(etudiantService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ENSEIGNANT')")
    public ResponseEntity<EtudiantResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(etudiantService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EtudiantResponse> create(
            @Valid @RequestBody EtudiantRequest req) {
        return ResponseEntity.ok(etudiantService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EtudiantResponse> update(
            @PathVariable Long id, @Valid @RequestBody EtudiantRequest req) {
        return ResponseEntity.ok(etudiantService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        etudiantService.delete(id);
        return ResponseEntity.ok("Étudiant supprimé");
    }
}