package delivery.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "colis")
public class Colis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive(message = "Le poids doit être positif")
    @Column(name = "poids", nullable = false)
    private Double poids;

    @Positive(message = "La longueur doit être positive")
    @Column(name = "longueur", nullable = false)
    private Double longueur;

    @Positive(message = "La largeur doit être positive")
    @Column(name = "largeur", nullable = false)
    private Double largeur;

    @Positive(message = "La hauteur doit être positive")
    @Column(name = "hauteur", nullable = false)
    private Double hauteur;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeColis type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_transport_id", nullable = false)
    private DemandeTransport demandeTransport;

    // Getters et Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public Double getLongueur() {
        return longueur;
    }

    public void setLongueur(Double longueur) {
        this.longueur = longueur;
    }

    public Double getLargeur() {
        return largeur;
    }

    public void setLargeur(Double largeur) {
        this.largeur = largeur;
    }

    public Double getHauteur() {
        return hauteur;
    }

    public void setHauteur(Double hauteur) {
        this.hauteur = hauteur;
    }

    public TypeColis getType() {
        return type;
    }

    public void setType(TypeColis type) {
        this.type = type;
    }

    public DemandeTransport getDemandeTransport() {
        return demandeTransport;
    }

    public void setDemandeTransport(DemandeTransport demandeTransport) {
        this.demandeTransport = demandeTransport;
    }
}
