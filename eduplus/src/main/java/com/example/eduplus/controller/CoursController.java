package com.example.eduplus.controller;

import com.example.eduplus.domain.dtos.CoursRequest;
import com.example.eduplus.domain.dtos.CoursResponse;
import com.example.eduplus.services.CoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cours")
@RequiredArgsConstructor
public class CoursController {

    private final CoursService coursService;

    @GetMapping
    public ResponseEntity<Page<CoursResponse>> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(coursService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoursResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CoursResponse> create(
            @Valid @RequestBody CoursRequest req) {
        return ResponseEntity.ok(coursService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CoursResponse> update(
            @PathVariable Long id, @Valid @RequestBody CoursRequest req) {
        return ResponseEntity.ok(coursService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        coursService.delete(id);
        return ResponseEntity.ok("Cours supprimé");
    }
}