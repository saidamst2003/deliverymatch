package delivery.example.backend.service;

import delivery.example.backend.model.AnnonceTrajet;
import delivery.example.backend.model.DemandeTransport;
import delivery.example.backend.model.Expediteur;
import delivery.example.backend.repository.AnnonceTrajetRepository;
import delivery.example.backend.repository.DemandeTransportRepository;
import delivery.example.backend.repository.ExpediteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DemandeTransportService{

    @Autowired
    private DemandeTransportRepository demandeTransportRepository;

    @Autowired
    private ExpediteurRepository expediteurRepository;

    @Autowired
    private AnnonceTrajetRepository annonceTrajetRepository;

    public DemandeTransport createDemande(DemandeTransport demande) {
        if (demande.getExpediteur() == null || demande.getExpediteur().getId() == null) {
            throw new RuntimeException("Expediteur ou son ID est manquant");
        }
        if (demande.getAnnonceTrajet() == null || demande.getAnnonceTrajet().getId() == null) {
            throw new RuntimeException("AnnonceTrajet ou son ID est manquant");
        }

        Expediteur expediteur = expediteurRepository.findById(demande.getExpediteur().getId())
                .orElseThrow(() -> new RuntimeException("Expediteur non trouvé"));

        AnnonceTrajet annonceTrajet = annonceTrajetRepository.findById(demande.getAnnonceTrajet().getId())
                .orElseThrow(() -> new RuntimeException("Annonce trajet non trouvée"));

        demande.setExpediteur(expediteur);
        demande.setAnnonceTrajet(annonceTrajet);

        return demandeTransportRepository.save(demande);
    }

    public List<DemandeTransport> getDemandesByExpediteur(Long expediteurId) {
        return demandeTransportRepository.findByExpediteur_Id(expediteurId);
    }




}
