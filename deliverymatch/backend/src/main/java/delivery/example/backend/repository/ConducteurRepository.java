package delivery.example.backend.repository;

import delivery.example.backend.model.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {

    Optional<Conducteur> findByEmail(String email);

}
