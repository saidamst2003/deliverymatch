package delivery.example.backend.service;

import delivery.example.backend.dto.DemandeTransportDto;
import delivery.example.backend.model.*;
import delivery.example.backend.repository.AnnonceTrajetRepository;
import delivery.example.backend.repository.DemandeTransportRepository;
import delivery.example.backend.repository.ExpediteurRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
@Service
public class DemandeTransportService {

    private final DemandeTransportRepository demandeTransportRepository;
    private final ExpediteurRepository expediteurRepository;
    private final AnnonceTrajetRepository annonceTrajetRepository;

    public DemandeTransportService(
            DemandeTransportRepository demandeTransportRepository,
            ExpediteurRepository expediteurRepository,
            AnnonceTrajetRepository annonceTrajetRepository
    ) {
        this.demandeTransportRepository = demandeTransportRepository;
        this.expediteurRepository = expediteurRepository;
        this.annonceTrajetRepository = annonceTrajetRepository;
    }

    public DemandeTransport createDemande(DemandeTransportDto dto) {
        if (dto.annonceTrajetId() == null) {
            throw new IllegalArgumentException("annonceTrajetId ne peut pas être null");
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Expediteur expediteur = expediteurRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Expéditeur non trouvé avec l'email: " + email));

        AnnonceTrajet annonce = annonceTrajetRepository.findById(dto.annonceTrajetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Annonce avec id " + dto.annonceTrajetId() + " non trouvée"));

        DemandeTransport demande = new DemandeTransport();
        demande.setExpediteur(expediteur);
        demande.setAnnonceTrajet(annonce);
        demande.setPoidsColis(dto.poidsColis());
        demande.setLargeurColis(dto.largeurColis());
        demande.setLongueurColis(dto.longueurColis());
        demande.setHauteurColis(dto.hauteurColis());
        demande.setTypeColis(dto.typeColis());
        demande.setDateDemande(LocalDate.now());
        demande.setStatut(dto.statut() != null ? dto.statut() : StatusDemande.EN_ATTENTE);

        return demandeTransportRepository.save(demande);
    }

    public List<DemandeTransport> getDemandesByExpediteur(Integer expediteurId) {
        return demandeTransportRepository.findByExpediteur_Id(Long.valueOf(expediteurId));
    }

    public DemandeTransport getDemandeById(Integer id) {
        return demandeTransportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Demande non trouvée avec id " + id));
    }


    public DemandeTransport updateDemande(Integer id, DemandeTransport updatedDemande) {
        DemandeTransport existing = getDemandeById(id);

        if (updatedDemande.getStatut() != null) {
            existing.setStatut(updatedDemande.getStatut());
        }

        return demandeTransportRepository.save(existing);
    }

    public void deleteDemande(Integer id) {
        DemandeTransport existing = getDemandeById(id);
        demandeTransportRepository.delete(existing);
    }
}