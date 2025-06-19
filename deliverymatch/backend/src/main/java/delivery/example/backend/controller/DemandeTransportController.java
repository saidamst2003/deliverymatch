package delivery.example.backend.controller;

import delivery.example.backend.model.DemandeTransport;
import delivery.example.backend.service.DemandeTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
public class DemandeTransportController {

    @Autowired
    private DemandeTransportService demandeService;

    @PostMapping
    public ResponseEntity<DemandeTransport> createDemande(@RequestBody DemandeTransport demande) {
        DemandeTransport created = demandeService.createDemande(demande);
        return ResponseEntity.ok(created);
    }
//get demandes by expiditeur
@GetMapping("/expediteur/{expediteurId}")
public ResponseEntity<List<DemandeTransport>> getDemandesByExpediteur(@PathVariable Long expediteurId) {
    List<DemandeTransport> demandes = demandeService.getDemandesByExpediteur(expediteurId);
    return ResponseEntity.ok(demandes);
}


    @GetMapping("/{id}")
    public ResponseEntity<DemandeTransport> getDemandeById(@PathVariable Integer id) {
        DemandeTransport demande = demandeService.getDemandeById(id);
        return ResponseEntity.ok(demande);
    }
}
