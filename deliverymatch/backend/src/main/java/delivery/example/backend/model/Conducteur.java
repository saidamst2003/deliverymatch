package delivery.example.backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "conducteurs")

public class Condicteur extends User {



//    // Relations
//    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<AnnonceTrajet> annoncesTrajet;
//
//    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<HistoriqueTrajet> historiqueTrajets;
}
