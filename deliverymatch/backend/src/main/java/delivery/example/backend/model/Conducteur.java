package delivery.example.backend.model;


import jakarta.persistence.*;
@DiscriminatorValue("Conducteur")
@Entity
@Table(name = "conducteurs")

public class Conducteur extends User {
    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnnonceTrajet> annoncesTrajet;

}
