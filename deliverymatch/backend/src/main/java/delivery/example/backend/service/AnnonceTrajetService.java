package delivery.example.backend.service;

import delivery.example.backend.dto.AnnonceTrajetDTO;
import delivery.example.backend.model.AnnonceTrajet;
import delivery.example.backend.model.Conducteur;
import delivery.example.backend.repository.AnnonceTrajetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service

public class AnnonceTrajetService {

    private final AnnonceTrajetRepository annonceTrajetRepository;

    public AnnonceTrajetService(AnnonceTrajetRepository annonceTrajetRepository) {
        this.annonceTrajetRepository = annonceTrajetRepository;
    }
    public AnnonceTrajet createAnnonceTrajet(AnnonceTrajetDTO dto, Conducteur conducteur) {
        AnnonceTrajet annonce = new AnnonceTrajet();

        annonce.setLieuDepart(dto.lieuDepart());
        annonce.setEtapesIntermediaires(dto.etapesIntermediaires());
        annonce.setDestination(dto.destination());
        annonce.setCapaciteDisponible(dto.capaciteDisponible());
        annonce.setTypeMarchandiseAcceptee(dto.typeMarchandiseAcceptee());

        annonce.setDateCreation(LocalDate.now());  // date du jour

        annonce.setConducteur(conducteur);

        return annonceTrajetRepository.save(annonce);
    }
}
