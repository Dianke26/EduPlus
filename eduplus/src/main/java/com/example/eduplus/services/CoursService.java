package com.example.eduplus.services;

import com.example.eduplus.domain.Cours;
import com.example.eduplus.domain.Enseignant;
import com.example.eduplus.domain.dtos.CoursRequest;
import com.example.eduplus.domain.dtos.CoursResponse;
import com.example.eduplus.exception.DuplicateResourceException;
import com.example.eduplus.exception.ResourceNotFoundException;
import com.example.eduplus.repository.CoursRepository;
import com.example.eduplus.repository.EnseignantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoursService {

    private final CoursRepository coursRepository;
    private final EnseignantRepository enseignantRepository;

    public Page<CoursResponse> findAll(Pageable pageable) {
        return coursRepository.findAll(pageable).map(this::toResponse);
    }

    public CoursResponse findById(Long id) {
        return toResponse(coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cours introuvable avec id " + id)));
    }

    public CoursResponse create(CoursRequest req) {
        if (coursRepository.existsByCode(req.getCode()))
            throw new DuplicateResourceException("Code cours deja utilise");

        Cours c = new Cours();
        mapToEntity(req, c);
        return toResponse(coursRepository.save(c));
    }

    public CoursResponse update(Long id, CoursRequest req) {
        Cours c = coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cours introuvable avec id " + id));
        mapToEntity(req, c);
        return toResponse(coursRepository.save(c));
    }

    public void delete(Long id) {
        if (!coursRepository.existsById(id))
            throw new ResourceNotFoundException(
                    "Cours introuvable avec id " + id);
        coursRepository.deleteById(id);
    }

    private void mapToEntity(CoursRequest req, Cours c) {
        Enseignant enseignant = enseignantRepository.findById(req.getEnseignantId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enseignant introuvable avec id " + req.getEnseignantId()));
        c.setCode(req.getCode());
        c.setNom(req.getNom());
        c.setCredits(req.getCredits());
        c.setEnseignant(enseignant);
    }

    private CoursResponse toResponse(Cours c) {
        String nomEnseignant = c.getEnseignant() != null
                ? c.getEnseignant().getPrenom() + " " + c.getEnseignant().getNom()
                : "Non assigne";
        return new CoursResponse(c.getId(), c.getCode(),
                c.getNom(), c.getCredits(), nomEnseignant);
    }
}