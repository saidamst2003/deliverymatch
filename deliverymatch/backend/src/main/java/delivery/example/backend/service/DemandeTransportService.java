package delivery.example.backend.service;

import delivery.example.backend.dto.DemandeTransportDto;
import delivery.example.backend.model.*;
import delivery.example.backend.repository.AnnonceTrajetRepository;
import delivery.example.backend.repository.DemandeTransportRepository;
import delivery.example.backend.repository.ExpediteurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DemandeTransportService {

    @Autowired
    private DemandeTransportRepository demandeTransportRepository;

    @Autowired
    private ExpediteurRepository expediteurRepository;

    @Autowired
    private AnnonceTrajetRepository annonceTrajetRepository;
    public DemandeTransport createDemande(DemandeTransportDto dto) {
        if (dto.expediteurId() == null) {
            throw new IllegalArgumentException("expediteurId ne peut pas être null");
        }
        if (dto.annonceTrajetId() == null) {
            throw new IllegalArgumentException("annonceTrajetId ne peut pas être null");
        }
        Expediteur expediteur = expediteurRepository.findById(Long.valueOf(dto.expediteurId()))
                .orElseThrow(() -> new EntityNotFoundException("Expediteur non trouvé"));
        AnnonceTrajet annonce = annonceTrajetRepository.findById(dto.annonceTrajetId())
                .orElseThrow(() -> new EntityNotFoundException("Annonce non trouvée"));

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
                .orElseThrow(() -> new NoSuchElementException("Demande non trouvée avec id " + id));
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