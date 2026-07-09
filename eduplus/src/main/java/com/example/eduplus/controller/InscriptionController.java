package com.example.eduplus.controller;

import com.example.eduplus.domain.dtos.InscriptionRequest;
import com.example.eduplus.domain.dtos.InscriptionResponse;
import com.example.eduplus.services.InscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ENSEIGNANT')")
    public ResponseEntity<Page<InscriptionResponse>> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(inscriptionService.findAll(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<InscriptionResponse> create(
            @Valid @RequestBody InscriptionRequest req) {
        return ResponseEntity.ok(inscriptionService.create(req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        inscriptionService.delete(id);
        return ResponseEntity.ok("Inscription supprimée");
    }
}