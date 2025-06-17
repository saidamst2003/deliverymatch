package delivery.example.backend.model;
public enum TypeColis {
    PETIT("Petit colis", 0.0, 5.0, "Moins de 5kg"),
    MOYEN("Colis moyen", 5.0, 15.0, "Entre 5kg et 15kg"),
    GRAND("Grand colis", 15.0, 30.0, "Entre 15kg et 30kg"),
    VOLUMINEUX("Colis volumineux", 30.0, 50.0, "Entre 30kg et 50kg"),
    TRES_VOLUMINEUX("Très volumineux", 50.0, Double.MAX_VALUE, "Plus de 50kg");

    private final String libelle;
    private final double poidsMin;
    private final double poidsMax;
    private final String description;

    TypeColis(String libelle, double poidsMin, double poidsMax, String description) {
        this.libelle = libelle;
        this.poidsMin = poidsMin;
        this.poidsMax = poidsMax;
        this.description = description;
    }

    public String getLibelle() {
        return libelle;
    }

    public double getPoidsMin() {
        return poidsMin;
    }

    public double getPoidsMax() {
        return poidsMax;
    }

    public String getDescription() {
        return description;
    }
//
//    public static TypeColis getByPoids(double poids) {
//        for (TypeColis type : values()) {
//            if (poids >= type.poidsMin && poids < type.poidsMax) {
//                return type;
//            }
//        }
//        return TRES_VOLUMINEUX;
//    }
}