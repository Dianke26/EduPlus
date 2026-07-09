package com.example.eduplus.controller;

import com.example.eduplus.domain.dtos.EnseignantRequest;
import com.example.eduplus.domain.dtos.EnseignantResponse;
import com.example.eduplus.services.EnseignantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
public class EnseignantController {

    private final EnseignantService enseignantService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Page<EnseignantResponse>> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(enseignantService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EnseignantResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(enseignantService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EnseignantResponse> create(
            @Valid @RequestBody EnseignantRequest req) {
        return ResponseEntity.ok(enseignantService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EnseignantResponse> update(
            @PathVariable Long id, @Valid @RequestBody EnseignantRequest req) {
        return ResponseEntity.ok(enseignantService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        enseignantService.delete(id);
        return ResponseEntity.ok("Enseignant supprimé");
    }
}