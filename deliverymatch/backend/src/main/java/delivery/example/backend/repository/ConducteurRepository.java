package delivery.example.backend.repository;

import delivery.example.backend.model.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {

    // Exemple : chercher un conducteur par email (utile pour l'authentification)
    Optional<Conducteur> findByEmail(String email);

}
