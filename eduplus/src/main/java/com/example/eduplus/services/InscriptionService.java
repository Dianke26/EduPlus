package com.example.eduplus.services;

import com.example.eduplus.domain.Cours;
import com.example.eduplus.domain.Etudiant;
import com.example.eduplus.domain.Inscription;
import com.example.eduplus.domain.dtos.InscriptionRequest;
import com.example.eduplus.domain.dtos.InscriptionResponse;
import com.example.eduplus.exception.DuplicateResourceException;
import com.example.eduplus.exception.ResourceNotFoundException;
import com.example.eduplus.repository.CoursRepository;
import com.example.eduplus.repository.EtudiantRepository;
import com.example.eduplus.repository.InscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;

    public Page<InscriptionResponse> findAll(Pageable pageable) {
        return inscriptionRepository.findAll(pageable).map(this::toResponse);
    }

    public InscriptionResponse create(InscriptionRequest req) {
        if (inscriptionRepository.existsByEtudiantIdAndCoursId(
                req.getEtudiantId(), req.getCoursId()))
            throw new DuplicateResourceException(
                    "Cet etudiant est deja inscrit a ce cours");

        Etudiant etudiant = etudiantRepository.findById(req.getEtudiantId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Etudiant introuvable avec id " + req.getEtudiantId()));
        Cours cours = coursRepository.findById(req.getCoursId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cours introuvable avec id " + req.getCoursId()));

        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setCours(cours);
        inscription.setDateInscription(LocalDate.now());
        return toResponse(inscriptionRepository.save(inscription));
    }

    public void delete(Long id) {
        if (!inscriptionRepository.existsById(id))
            throw new ResourceNotFoundException(
                    "Inscription introuvable avec id " + id);
        inscriptionRepository.deleteById(id);
    }

    private InscriptionResponse toResponse(Inscription i) {
        String nomEtudiant = i.getEtudiant().getPrenom()
                + " " + i.getEtudiant().getNom();
        return new InscriptionResponse(i.getId(), nomEtudiant,
                i.getCours().getNom(), i.getDateInscription());
    }
}