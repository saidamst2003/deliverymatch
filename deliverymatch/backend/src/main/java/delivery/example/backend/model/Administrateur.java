package delivery.example.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@DiscriminatorValue("Administrateur")
@Entity
@Table(name = "Administrateurs")

public class Administrateur extends User {
}
