package delivery.example.backend.dto;
package delivery.example.backend.dto;

import delivery.example.backend.model.TypeMarchandise;

import java.time.LocalDate;
import java.util.List;

public record AnnonceTrajetDTO(
        String lieuDepart,
        List<String> etapes,
        String destination,
        double longueurMax,
        double largeurMax,
        double hauteurMax,
        TypeMarchandise typeMarchandise,
        double capaciteDisponible
) {}
