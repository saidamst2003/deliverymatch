package delivery.example.backend.repository;

import delivery.example.backend.model.AnnonceTrajet;
import delivery.example.backend.model.TypeMarchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AnnonceTrajetRepository extends JpaRepository<AnnonceTrajet, Integer> {

    @Query("SELECT a FROM AnnonceTrajet a " +
            "WHERE (:destination IS NULL OR a.destination = :destination) " +
            "AND (:dateCreation IS NULL OR a.dateCreation = :dateCreation) " +
            "AND (:typeMarchandise IS NULL OR a.typeMarchandiseAcceptee = :typeMarchandise)")
    List<AnnonceTrajet> findByCriteria(@Param("destination") String destination,
                                       @Param("dateCreation") LocalDate dateCreation,
                                       @Param("typeMarchandise") TypeMarchandise typeMarchandise);
}

