package delivery.example.backend.service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import delivery.example.backend.dto.AnnonceTrajetDTO;
import delivery.example.backend.model.AnnonceTrajet;
import delivery.example.backend.model.Conducteur;
import delivery.example.backend.model.TypeMarchandise;
import delivery.example.backend.repository.AnnonceTrajetRepository;
import delivery.example.backend.repository.ConducteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceTrajetService {

    private final AnnonceTrajetRepository annonceTrajetRepository;
    private final ConducteurRepository conducteurRepository;

    // Constructeur pour l'injection de dépendances
    @Autowired
    public AnnonceTrajetService(AnnonceTrajetRepository annonceTrajetRepository, ConducteurRepository conducteurRepository) {
        this.annonceTrajetRepository = annonceTrajetRepository;
        this.conducteurRepository = conducteurRepository;
    }

    // Publie une nouvelle annonce de trajet en associant un conducteur existant
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

    // Recherche les annonces de trajet selon des critères (destination, date de création, type de marchandise)
    public List<AnnonceTrajet> chercherAnnonces(String destination, LocalDate dateCreation, TypeMarchandise typeMarchandise) {
        return annonceTrajetRepository.findByCriteria(destination, dateCreation, typeMarchandise);
    }


    public List<AnnonceTrajetDTO> getAllAnnoncesConducteurs() {
        List<AnnonceTrajet> annonces = annonceTrajetRepository.findAllByConducteurIsNotNull();

        return annonces.stream()
                .map(a -> new AnnonceTrajetDTO(
                        a.getId(),
                        a.getLieuDepart(),
                        a.getEtapesIntermediaires(),
                        a.getDestination(),
                        a.getCapaciteDisponible(),
                        a.getDateCreation(),
                        a.getTypeMarchandiseAcceptee()
                ))
                .toList();
    }

//modifier un annonce

    public AnnonceTrajet updateAnnonce(Integer id, AnnonceTrajetDTO updatedAnnonceDto) {

        AnnonceTrajet annonce = annonceTrajetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Annonce non trouvée avec id " + id));
        annonce.setLieuDepart(updatedAnnonceDto.lieuDepart());
        annonce.setDestination(updatedAnnonceDto.destination());
        annonce.setCapaciteDisponible(updatedAnnonceDto.capaciteDisponible());
        annonce.setTypeMarchandiseAcceptee(updatedAnnonceDto.typeMarchandiseAcceptee());
        annonce.setEtapesIntermediaires(updatedAnnonceDto.etapesIntermediaires());

        return annonceTrajetRepository.save(annonce);
    }

    // Trouve une annonce de trajet par son ID
    public AnnonceTrajet findById(Integer id) {
        return annonceTrajetRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Annonce non trouvée avec id " + id));
    }
    // Supprime une annonce de trajet par son ID
    public void delete(Integer id) {
        AnnonceTrajet annonce = findById(id);
        annonceTrajetRepository.delete(annonce);
    }

}
