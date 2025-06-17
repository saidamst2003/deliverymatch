package delivery.example.backend.model;

public enum TypeMarchandise {
    ELECTRONIQUE("Électronique", true),
    TEXTILE("Textile et vêtements", false),
    ALIMENTAIRE("Produits alimentaires", true),
    FRAGILE("Objets fragiles", true),
    DOCUMENTS("Documents et papiers", false),
    LIVRES("Livres et publications", false),
    MOBILIER("Mobilier et décoration", false),
    MEDICAL("Matériel médical", true),
    AUTOMOBILE("Pièces automobiles", false),
    SPORT("Équipement sportif", false),
    AUTRE("Autre type", false);

    private final String libelle;
    private final boolean necessiteAutorisation;

    TypeMarchandise(String libelle, boolean necessiteAutorisation) {
        this.libelle = libelle;
        this.necessiteAutorisation = necessiteAutorisation;
    }

    public String getLibelle() {
        return libelle;
    }

    public boolean isNecessiteAutorisation() {
        return necessiteAutorisation;
    }
}
