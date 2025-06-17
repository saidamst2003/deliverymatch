package delivery.example.backend.model;

public enum StatusDemande {
    EN_ATTENTE("En attente de réponse"),
    ACCEPTEE("Demande acceptée"),
    REFUSEE("Demande refusée"),
    EN_COURS("Transport en cours"),
    LIVREE("Colis livré"),
    ANNULEE("Demande annulée");

    private final String libelle;

    StatusDemande(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}