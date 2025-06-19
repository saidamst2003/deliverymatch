package delivery.example.backend.controller;

import delivery.example.backend.dto.DemandeTransportDto;
import delivery.example.backend.model.DemandeTransport;
import delivery.example.backend.service.DemandeTransportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
public class DemandeTransportController {

    @Autowired
    private DemandeTransportService demandeService;

    // Crée une nouvelle demande de transport aveec colis
    @PostMapping

    public ResponseEntity<DemandeTransport> createDemande(@RequestBody DemandeTransportDto dto) {
        DemandeTransport demande = demandeService.createDemande(dto);
        return ResponseEntity.ok(demande);
    }


    // Récupère les demandes de transport par ID d'expéditeur
    @GetMapping("/expediteur/{expediteurId}")
    public ResponseEntity<List<DemandeTransport>> getDemandesByExpediteur(@PathVariable Integer expediteurId) {
        List<DemandeTransport> demandes = demandeService.getDemandesByExpediteur(expediteurId);
        return ResponseEntity.ok(demandes);
    }

    // Récupère une demande de transport par son ID
    @GetMapping("/{id}")
    public ResponseEntity<DemandeTransport> getDemandeById(@PathVariable Integer id) {
        DemandeTransport demande = demandeService.getDemandeById(id);
        return ResponseEntity.ok(demande);
    }

    // Met à jour une demande de transport existante
    @PutMapping("/{id}")
    public ResponseEntity<DemandeTransport> updateDemande(@PathVariable Integer id, @RequestBody DemandeTransport demande) {
        DemandeTransport updated = demandeService.updateDemande(id, demande);
        return ResponseEntity.ok(updated);
    }

    // Supprime une demande de transport par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Integer id) {
        demandeService.deleteDemande(id);
        return ResponseEntity.noContent().build();
    }
}
