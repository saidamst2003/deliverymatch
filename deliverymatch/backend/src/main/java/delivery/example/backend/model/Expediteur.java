package delivery.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@DiscriminatorValue("EXPEDITEUR")
@Entity

public class Expediteur extends User {

    @OneToMany(mappedBy = "expediteur")
    private List<DemandeTransport> demandesTransport;

}
