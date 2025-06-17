package delivery.example.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@DiscriminatorValue("ADMIN")
@Entity

public class Administrateur extends User {
}
