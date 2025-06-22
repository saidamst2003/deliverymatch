package delivery.example.backend.controller;

import delivery.example.backend.dto.AnnonceTrajetDTO;
import delivery.example.backend.model.AnnonceTrajet;
import delivery.example.backend.model.Conducteur;
import delivery.example.backend.model.TypeMarchandise;
import delivery.example.backend.repository.AnnonceTrajetRepository;
import delivery.example.backend.service.AnnonceTrajetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/annonces-trajet")
@CrossOrigin(origins = "http://localhost:4200")

public class AnnonceTrajetController {

    private final AnnonceTrajetService annonceTrajetService;
private final AnnonceTrajetRepository annonceTrajetRepository;
    // Constructeur pour l'injection de dépendances
    @Autowired
    public AnnonceTrajetController(AnnonceTrajetService annonceTrajetService, AnnonceTrajetRepository annonceTrajetRepository) {
        this.annonceTrajetService = annonceTrajetService;
        this.annonceTrajetRepository = annonceTrajetRepository;
    }

    // Publie une nouvelle annonce de trajet pour un conducteur donné
    @PostMapping("/publier/{conducteurId}")

    public ResponseEntity<?> publierAnnonce(
            @PathVariable Long conducteurId,
            @Valid @RequestBody AnnonceTrajetDTO annonceDTO) {
        try {
            AnnonceTrajet nouvelleAnnonce = annonceTrajetService.publierAnnonce(annonceDTO, conducteurId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleAnnonce);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Cherche des annonces de trajet selon des critères (destination, date, type de marchandise)
    @GetMapping("/search")
    public ResponseEntity<List<AnnonceTrajet>> chercherAnnonces(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateCreation,
            @RequestParam(required = false) TypeMarchandise typeMarchandise) {

        List<AnnonceTrajet> resultats = annonceTrajetService.chercherAnnonces(destination, dateCreation, typeMarchandise);
        return ResponseEntity.ok(resultats);
    }

    // Récupère toutes les annonces de trajet pour les conducteurs (accès admin)
//    @GetMapping("/mes-annonces")
//    public ResponseEntity<List<AnnonceTrajet>> getMesAnnonces(Authentication authentication) {
//        String email = authentication.getName(); // أو كيفما كتخزن اسم المستخدم فالتوكن
//        Conducteur conducteur = conducteurService.findByEmail(email);
//        if (conducteur == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        List<AnnonceTrajet> annonces = annonceTrajetRepository.findByConducteur(conducteur);
//        return ResponseEntity.ok(annonces);
//    }



    // Modifie une annonce de trajet existante (accès admin)
    @PutMapping("/admin/annonces-conducteurs/{id}")
    public ResponseEntity<AnnonceTrajet> updateAnnonce(
            @PathVariable Integer id,
            @RequestBody AnnonceTrajet updatedAnnonce) {
        AnnonceTrajet annonce = annonceTrajetService.updateAnnonce(id, updatedAnnonce);
        return ResponseEntity.ok(annonce);
    }

    // Supprime une annonce de trajet par son ID (accès admin)
    @DeleteMapping("/admin/annonces-conducteurs/{id}")
    public ResponseEntity<?> deleteAnnonce(@PathVariable Integer id) {
        annonceTrajetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
