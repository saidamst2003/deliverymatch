package delivery.example.backend.repository;

import delivery.example.backend.model.DemandeTransport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeTransportRepository extends JpaRepository<DemandeTransport, Integer> {
    List<DemandeTransport> findByExpediteurId(Integer expediteurId);
}
