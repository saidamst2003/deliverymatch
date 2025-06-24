package delivery.example.backend.controller;

import delivery.example.backend.dto.DemandeTransportDto;
import delivery.example.backend.model.DemandeTransport;
import delivery.example.backend.service.DemandeTransportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/demandes")
public class DemandeTransportController {

    private final DemandeTransportService demandeTransportService;

    public DemandeTransportController(DemandeTransportService demandeTransportService) {
        this.demandeTransportService = demandeTransportService;
    }

    // Créer une nouvelle demande de transport avec colis
    @PostMapping
    public ResponseEntity<DemandeTransport> createDemande(@Valid @RequestBody DemandeTransportDto dto) {
        DemandeTransport demande = demandeTransportService.createDemande(dto);
        return ResponseEntity.ok(demande);
    }

    // Récupérer les demandes par ID d'expéditeur

    @GetMapping("/expediteur/{expediteurId}")
    public ResponseEntity<List<DemandeTransport>> getDemandesByExpediteur(@PathVariable Integer expediteurId) {
        List<DemandeTransport> demandes = demandeTransportService.getDemandesByExpediteur(expediteurId);
        return ResponseEntity.ok(demandes);
    }

    // Récupérer une demande par ID
    @GetMapping("/demandes/{id}")
    public ResponseEntity<DemandeTransport> getDemandeById(@PathVariable Integer id) {
        try {
            DemandeTransport demande = demandeTransportService.getDemandeById(id);
            return ResponseEntity.ok(demande);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // Mettre à jour une demande
    @PutMapping("/{id}")
    public ResponseEntity<DemandeTransport> updateDemande(@PathVariable Integer id, @RequestBody DemandeTransport demande) {
        DemandeTransport updated = demandeTransportService.updateDemande(id, demande);
        return ResponseEntity.ok(updated);
    }

    // Supprimer une demande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Integer id) {
        demandeTransportService.deleteDemande(id);
        return ResponseEntity.noContent().build();
    }
}
