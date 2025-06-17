package delivery.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "annonces_trajet")
public class AnnonceTrajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Le lieu de départ est obligatoire")
    @Column(name = "lieu_depart", nullable = false)
    private String lieuDepart;

    @ElementCollection
    @CollectionTable(name = "etapes_intermediaires",
            joinColumns = @JoinColumn(name = "annonce_id"))
    @Column(name = "etape")
    private List<String> etapesIntermediaires;

    @NotBlank(message = "La destination est obligatoire")
    @Column(name = "destination", nullable = false)
    private String destination;

    @Positive(message = "La capacité disponible doit être positive")
    @Column(name = "capacite_disponible", nullable = false)
    private Double capaciteDisponible;

    @NotNull(message = "La date de création est obligatoire")
    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_marchandise_acceptee")
    private TypeMarchandise typeMarchandiseAcceptee;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conducteur_id", nullable = false)
    private Conducteur conducteur;

    @OneToMany(mappedBy = "annonceTrajet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DemandeTransport> demandesTransport;

}
