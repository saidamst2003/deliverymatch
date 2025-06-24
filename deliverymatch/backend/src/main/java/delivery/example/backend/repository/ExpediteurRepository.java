package delivery.example.backend.repository;

import delivery.example.backend.model.Expediteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpediteurRepository extends JpaRepository<Expediteur, Long> {
    Optional<Expediteur> findByEmail(String email);

}
