package delivery.example.backend.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "demandes_transport")

public class DemandeTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive(message = "Le poids doit être positif")
    @Column(name = "poids_colis", nullable = false)
    private Double poidsColis;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatusDemande statut = StatusDemande.EN_ATTENTE;

    @NotNull(message = "La date de demande est obligatoire")
    @Column(name = "date_demande", nullable = false)
    private LocalDate dateDemande;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "expediteur_id", nullable = false)
    private Expediteur expediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "annonce_trajet_id", nullable = false)
    private AnnonceTrajet annonceTrajet;

    @OneToMany(mappedBy = "demandeTransport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Colis> colis;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @Positive(message = "Le poids doit être positif") Double getPoidsColis() {
        return poidsColis;
    }

    public void setPoidsColis(@Positive(message = "Le poids doit être positif") Double poidsColis) {
        this.poidsColis = poidsColis;
    }

    public StatusDemande getStatut() {
        return statut;
    }

    public void setStatut(StatusDemande statut) {
        this.statut = statut;
    }

    public @NotNull(message = "La date de demande est obligatoire") LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(@NotNull(message = "La date de demande est obligatoire") LocalDate dateDemande) {
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

    public List<Colis> getColis() {
        return colis;
    }

    public void setColis(List<Colis> colis) {
        this.colis = colis;
    }
}
