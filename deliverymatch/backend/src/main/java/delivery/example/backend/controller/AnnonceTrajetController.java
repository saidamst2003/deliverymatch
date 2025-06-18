package delivery.example.backend.controller;

import delivery.example.backend.dto.AnnonceTrajetDTO;
import delivery.example.backend.model.AnnonceTrajet;
import delivery.example.backend.service.AnnonceTrajetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/annonces-trajet")
@CrossOrigin(origins = "")
public class AnnonceTrajetController {

    private final AnnonceTrajetService annonceTrajetService;

    @Autowired
    public AnnonceTrajetController(AnnonceTrajetService annonceTrajetService) {
        this.annonceTrajetService = annonceTrajetService;
    }

    @PostMapping("/publier/{conducteurId}")
    public ResponseEntity<?> publierAnnonce(
            @PathVariable Integer conducteurId,
            @Valid @RequestBody AnnonceTrajetDTO annonceDTO) {
        try {
            AnnonceTrajet nouvelleAnnonce = annonceTrajetService.publierAnnonce(annonceDTO, conducteurId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleAnnonce);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}