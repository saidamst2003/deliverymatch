package delivery.example.backend.model;

public enum StatusDemande {
    EN_ATTENTE("En attente de réponse"),
    ACCEPTEE("Demande acceptée"),
    REFUSEE("Demande refusée");


    private final String libelle;

    StatusDemande(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}