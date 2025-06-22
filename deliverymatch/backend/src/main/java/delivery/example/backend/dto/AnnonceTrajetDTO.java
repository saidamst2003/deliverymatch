package delivery.example.backend.dto;

import delivery.example.backend.model.TypeMarchandise;
import java.time.LocalDate;
import java.util.List;

public record AnnonceTrajetDTO(
        Integer id,
        String lieuDepart,
        List<String> etapesIntermediaires,
        String destination,
        Double capaciteDisponible,
        LocalDate dateCreation,
        TypeMarchandise typeMarchandiseAcceptee
) {}
