package com.example.eduplus.controller;

import com.example.eduplus.domain.dtos.EtudiantRequest;
import com.example.eduplus.domain.dtos.EtudiantResponse;
import com.example.eduplus.services.EtudiantService;
import com.example.eduplus.services.FileStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

    private final EtudiantService etudiantService;
    private final FileStorageService fileStorageService;

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

    @PostMapping("/{id}/photo")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ETUDIANT')")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String photoUrl = fileStorageService.storePhoto(file);
        etudiantService.updatePhotoUrl(id, photoUrl);
        return ResponseEntity.ok("Photo uploadée avec succès : " + photoUrl);
    }

    @PostMapping("/{id}/docs")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ETUDIANT')")
    public ResponseEntity<String> uploadDocument(
            @PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String documentUrl = fileStorageService.storeDocument(file);
        etudiantService.updateDocumentUrl(id, documentUrl);
        return ResponseEntity.ok("Document uploadé avec succès : " + documentUrl);
    }
}