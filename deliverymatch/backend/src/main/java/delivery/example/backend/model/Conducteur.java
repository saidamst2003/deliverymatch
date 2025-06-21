package delivery.example.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.util.List;

@Entity
@DiscriminatorValue("CONDUCTEUR")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Conducteur extends User {
    @OneToMany(mappedBy = "conducteur")
    private List<AnnonceTrajet> annoncesTrajet;
}
