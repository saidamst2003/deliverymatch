package delivery.example.backend.dto;

import delivery.example.backend.model.StatusDemande;
import delivery.example.backend.model.TypeColis;

import java.time.LocalDate;

public record DemandeTransportDto(
        Integer id,
        Double poidsColis,
        Double largeurColis,
        Double longueurColis,
        Double hauteurColis,
        TypeColis typeColis,
        StatusDemande statut,
        LocalDate dateDemande,
        Integer annonceTrajetId
) {}
