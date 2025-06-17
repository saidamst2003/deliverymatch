package delivery.example.backend.model;



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
    @JoinColumn(name = "expediteur_id", nullable = false)
    private Expediteur expediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_trajet_id", nullable = false)
    private AnnonceTrajet annonceTrajet;

    @OneToMany(mappedBy = "demandeTransport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Colis> colis;
}
