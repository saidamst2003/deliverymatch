package delivery.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity
@Table(name = "demandes_transport")
public class DemandeTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive(message = "Le poids doit être positif")
    @Column(name = "poids_colis", nullable = false)
    private Double poidsColis;

    @Positive(message = "La largeur doit être positive")
    @Column(name = "largeur_colis", nullable = false)
    private Double largeurColis;

    @Positive(message = "La longueur doit être positive")
    @Column(name = "longueur_colis", nullable = false)
    private Double longueurColis;

    @Positive(message = "La hauteur doit être positive")
    @Column(name = "hauteur_colis", nullable = false)
    private Double hauteurColis;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_colis", nullable = false)
    private TypeColis typeColis;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatusDemande statut = StatusDemande.EN_ATTENTE;

    @NotNull(message = "La date de demande est obligatoire")
    @Column(name = "date_demande", nullable = false)
    private LocalDate dateDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expediteur_id", nullable = false)
    @JsonBackReference("demande-expediteur")
    private Expediteur expediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_trajet_id", nullable = false)
    @JsonBackReference("demande-annonce")
    private AnnonceTrajet annonceTrajet;

    // Getters & Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPoidsColis() {
        return poidsColis;
    }

    public void setPoidsColis(Double poidsColis) {
        this.poidsColis = poidsColis;
    }

    public Double getLargeurColis(Double aDouble) {
        return largeurColis;
    }

    public void setLargeurColis(Double largeurColis) {
        this.largeurColis = largeurColis;
    }

    public Double getLongueurColis(Double aDouble) {
        return longueurColis;
    }

    public void setLongueurColis(Double longueurColis) {
        this.longueurColis = longueurColis;
    }

    public Double getHauteurColis() {
        return hauteurColis;
    }

    public void setHauteurColis(Double hauteurColis) {
        this.hauteurColis = hauteurColis;
    }

    public TypeColis getTypeColis() {
        return typeColis;
    }

    public void setTypeColis(TypeColis typeColis) {
        this.typeColis = typeColis;
    }

    public StatusDemande getStatut() {
        return statut;
    }

    public void setStatut(StatusDemande statut) {
        this.statut = statut;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Expediteur getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Expediteur expediteur) {
        this.expediteur = expediteur;
    }

    public AnnonceTrajet getAnnonceTrajet() {
        return annonceTrajet;
    }

    public void setAnnonceTrajet(AnnonceTrajet annonceTrajet) {
        this.annonceTrajet = annonceTrajet;
    }
}
