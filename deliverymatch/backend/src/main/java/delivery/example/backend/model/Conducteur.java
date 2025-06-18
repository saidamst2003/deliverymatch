package delivery.example.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.util.List;

@DiscriminatorValue("CONDUCTEUR")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Conducteur extends User {
    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnnonceTrajet> annoncesTrajet;

}
