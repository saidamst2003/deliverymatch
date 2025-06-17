package delivery.example.backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.util.List;

@DiscriminatorValue("Conducteur")
@Entity
@Table(name = "conducteurs")

public class Conducteur extends User {
    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnnonceTrajet> annoncesTrajet;

}
