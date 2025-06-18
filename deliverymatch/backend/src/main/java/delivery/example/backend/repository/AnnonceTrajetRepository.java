package delivery.example.backend.repository;

import delivery.example.backend.model.AnnonceTrajet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceTrajetRepository extends JpaRepository<AnnonceTrajet, Integer> {
}

