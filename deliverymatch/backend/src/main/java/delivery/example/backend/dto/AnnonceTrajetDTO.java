package delivery.example.backend.dto;

import delivery.example.backend.model.TypeMarchandise;
import java.util.List;
public record AnnonceTrajetDTO(
        String lieuDepart,
        List<String> etapesIntermediaires,
        String destination,
        Double capaciteDisponible,
        TypeMarchandise typeMarchandiseAcceptee
) {}
