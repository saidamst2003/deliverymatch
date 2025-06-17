package delivery.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "colis")

public class Colis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive(message = "La largeur doit être positive")
    @Column(name = "largeur", nullable = false)
    private Double largeur;

    @Positive(message = "Le poids doit être positif")
    @Column(name = "poids", nullable = false)
    private String poids;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeColis type;

    @Positive(message = "La longueur doit être positive")
    @Column(name = "longueur", nullable = false)
    private Double longueur;

    @Positive(message = "La hauteur doit être positive")
    @Column(name = "hauteur", nullable = false)
    private Double hauteur;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_transport_id", nullable = false)
    private DemandeTransport demandeTransport;
}