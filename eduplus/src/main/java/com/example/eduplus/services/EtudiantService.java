package com.example.eduplus.services;

import com.example.eduplus.domain.Etudiant;
import com.example.eduplus.domain.dtos.EtudiantRequest;
import com.example.eduplus.domain.dtos.EtudiantResponse;
import com.example.eduplus.exception.DuplicateResourceException;
import com.example.eduplus.exception.ResourceNotFoundException;
import com.example.eduplus.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    public Page<EtudiantResponse> findAll(Pageable pageable) {
        return etudiantRepository.findAll(pageable).map(this::toResponse);
    }

    public EtudiantResponse findById(Long id) {
        return toResponse(etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Etudiant introuvable avec id " + id)));
    }

    public EtudiantResponse create(EtudiantRequest req) {
        if (etudiantRepository.existsByMatricule(req.getMatricule()))
            throw new DuplicateResourceException("Matricule deja utilise");
        if (etudiantRepository.existsByEmail(req.getEmail()))
            throw new DuplicateResourceException("Email deja utilise");

        Etudiant e = new Etudiant();
        mapToEntity(req, e);
        return toResponse(etudiantRepository.save(e));
    }

    public EtudiantResponse update(Long id, EtudiantRequest req) {
        Etudiant e = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Etudiant introuvable avec id " + id));
        mapToEntity(req, e);
        return toResponse(etudiantRepository.save(e));
    }

    public void delete(Long id) {
        if (!etudiantRepository.existsById(id))
            throw new ResourceNotFoundException(
                    "Etudiant introuvable avec id " + id);
        etudiantRepository.deleteById(id);
    }

    public void updatePhotoUrl(Long id, String photoUrl) {
        Etudiant e = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Etudiant introuvable avec id " + id));
        e.setPhotoUrl(photoUrl);
        etudiantRepository.save(e);
    }

    public void updateDocumentUrl(Long id, String documentUrl) {
        Etudiant e = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Etudiant introuvable avec id " + id));
        e.setDocumentUrl(documentUrl);
        etudiantRepository.save(e);
    }

    private void mapToEntity(EtudiantRequest req, Etudiant e) {
        e.setMatricule(req.getMatricule());
        e.setNom(req.getNom());
        e.setPrenom(req.getPrenom());
        e.setEmail(req.getEmail());
        e.setDateNaissance(req.getDateNaissance());
        e.setClasse(req.getClasse());
    }

    private EtudiantResponse toResponse(Etudiant e) {
        return new EtudiantResponse(e.getId(), e.getMatricule(),
                e.getNom(), e.getPrenom(), e.getEmail(),
                e.getDateNaissance(), e.getClasse(),
                e.getPhotoUrl(), e.getDocumentUrl());
    }
}