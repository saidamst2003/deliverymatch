package delivery.example.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

public class Expediteur  extends User {

    // Relations
    @OneToMany(mappedBy = "expediteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DemandeTransport> demandesTransport;

}
