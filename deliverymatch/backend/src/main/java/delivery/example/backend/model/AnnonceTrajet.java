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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "Le lieu de départ est obligatoire") String getLieuDepart() {
        return lieuDepart;
    }

    public void setLieuDepart(@NotBlank(message = "Le lieu de départ est obligatoire") String lieuDepart) {
        this.lieuDepart = lieuDepart;
    }

    public List<String> getEtapesIntermediaires() {
        return etapesIntermediaires;
    }

    public void setEtapesIntermediaires(List<String> etapesIntermediaires) {
        this.etapesIntermediaires = etapesIntermediaires;
    }

    public @NotBlank(message = "La destination est obligatoire") String getDestination() {
        return destination;
    }

    public void setDestination(@NotBlank(message = "La destination est obligatoire") String destination) {
        this.destination = destination;
    }

    public @Positive(message = "La capacité disponible doit être positive") Double getCapaciteDisponible() {
        return capaciteDisponible;
    }

    public void setCapaciteDisponible(@Positive(message = "La capacité disponible doit être positive") Double capaciteDisponible) {
        this.capaciteDisponible = capaciteDisponible;
    }

    public @NotNull(message = "La date de création est obligatoire") LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(@NotNull(message = "La date de création est obligatoire") LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public TypeMarchandise getTypeMarchandiseAcceptee() {
        return typeMarchandiseAcceptee;
    }

    public void setTypeMarchandiseAcceptee(TypeMarchandise typeMarchandiseAcceptee) {
        this.typeMarchandiseAcceptee = typeMarchandiseAcceptee;
    }

    public Conducteur getConducteur() {
        return conducteur;
    }

    public void setConducteur(Conducteur conducteur) {
        this.conducteur = conducteur;
    }

    public List<DemandeTransport> getDemandesTransport() {
        return demandesTransport;
    }

    public void setDemandesTransport(List<DemandeTransport> demandesTransport) {
        this.demandesTransport = demandesTransport;
    }
}
