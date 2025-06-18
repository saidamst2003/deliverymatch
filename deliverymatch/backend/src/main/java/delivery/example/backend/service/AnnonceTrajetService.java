package delivery.example.backend.service;

import delivery.example.backend.dto.AnnonceTrajetDTO;
import delivery.example.backend.model.AnnonceTrajet;
import delivery.example.backend.model.Conducteur;
import delivery.example.backend.model.TypeMarchandise;
import delivery.example.backend.repository.AnnonceTrajetRepository;
import delivery.example.backend.repository.ConducteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceTrajetService {

    private final AnnonceTrajetRepository annonceTrajetRepository;
    private final ConducteurRepository conducteurRepository;

    @Autowired
    public AnnonceTrajetService(AnnonceTrajetRepository annonceTrajetRepository, ConducteurRepository conducteurRepository) {
        this.annonceTrajetRepository = annonceTrajetRepository;
        this.conducteurRepository = conducteurRepository;
    }

    public AnnonceTrajet publierAnnonce(AnnonceTrajetDTO annonceDTO, Long conducteurId) {
        Optional<Conducteur> conducteurOpt = conducteurRepository.findById(conducteurId);
        if (conducteurOpt.isEmpty()) {
            throw new RuntimeException("Conducteur introuvable avec l'ID : " + conducteurId);
        }

        Conducteur conducteur = conducteurOpt.get();

        AnnonceTrajet annonce = new AnnonceTrajet();
        annonce.setLieuDepart(annonceDTO.lieuDepart());
        annonce.setDestination(annonceDTO.destination());
        annonce.setEtapesIntermediaires(annonceDTO.etapesIntermediaires());
        annonce.setCapaciteDisponible(annonceDTO.capaciteDisponible());
        annonce.setTypeMarchandiseAcceptee(annonceDTO.typeMarchandiseAcceptee());
        annonce.setDateCreation(LocalDate.now());
        annonce.setConducteur(conducteur);

        return annonceTrajetRepository.save(annonce);
    }

   //recherche
    public AnnonceTrajetService(AnnonceTrajetRepository annonceTrajetRepository) {
        this.annonceTrajetRepository = annonceTrajetRepository;
    }

    public List<AnnonceTrajet> chercherAnnonces(String destination, LocalDate dateCreation, TypeMarchandise typeMarchandise) {
        return annonceTrajetRepository.findByCriteria(destination, dateCreation, typeMarchandise);
    }

}
