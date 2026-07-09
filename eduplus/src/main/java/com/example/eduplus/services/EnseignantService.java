package com.example.eduplus.services;

import com.example.eduplus.domain.Enseignant;
import com.example.eduplus.domain.dtos.EnseignantRequest;
import com.example.eduplus.domain.dtos.EnseignantResponse;
import com.example.eduplus.exception.DuplicateResourceException;
import com.example.eduplus.exception.ResourceNotFoundException;
import com.example.eduplus.repository.EnseignantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;

    public Page<EnseignantResponse> findAll(Pageable pageable) {
        return enseignantRepository.findAll(pageable).map(this::toResponse);
    }

    public EnseignantResponse findById(Long id) {
        return toResponse(enseignantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enseignant introuvable avec id " + id)));
    }

    public EnseignantResponse create(EnseignantRequest req) {
        if (enseignantRepository.existsByEmail(req.getEmail()))
            throw new DuplicateResourceException("Email deja utilise");

        Enseignant e = new Enseignant();
        mapToEntity(req, e);
        return toResponse(enseignantRepository.save(e));
    }

    public EnseignantResponse update(Long id, EnseignantRequest req) {
        Enseignant e = enseignantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enseignant introuvable avec id " + id));
        mapToEntity(req, e);
        return toResponse(enseignantRepository.save(e));
    }

    public void delete(Long id) {
        if (!enseignantRepository.existsById(id))
            throw new ResourceNotFoundException(
                    "Enseignant introuvable avec id " + id);
        enseignantRepository.deleteById(id);
    }

    private void mapToEntity(EnseignantRequest req, Enseignant e) {
        e.setMatricule(req.getMatricule());
        e.setNom(req.getNom());
        e.setPrenom(req.getPrenom());
        e.setEmail(req.getEmail());
        e.setSpecialite(req.getSpecialite());
    }

    private EnseignantResponse toResponse(Enseignant e) {
        return new EnseignantResponse(e.getId(), e.getMatricule(),
                e.getNom(), e.getPrenom(), e.getEmail(), e.getSpecialite());
    }
}