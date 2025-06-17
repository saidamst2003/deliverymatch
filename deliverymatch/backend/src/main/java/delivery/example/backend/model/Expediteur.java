package delivery.example.backend.model;

import jakarta.persistence.*;

import java.util.List;
@DiscriminatorValue("EXPEDITEUR")
@Entity

public class Expediteur  extends User {

    // Relations
    @OneToMany(mappedBy = "expediteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DemandeTransport> demandesTransport;

}
