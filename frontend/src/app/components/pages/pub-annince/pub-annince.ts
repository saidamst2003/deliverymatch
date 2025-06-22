import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnonceTrajetDTO } from "../../../models/AnnonceTrajetDTO";
import { AnnonceService } from "../../../services/annonce";

@Component({
  selector: 'app-pun-announce',
  templateUrl: './pub-annince.html',
  styleUrls: ['./pub-annince.css'],
  standalone: true,
  imports: [CommonModule]
})
export class PubAnnince implements OnInit {
  annonces: AnnonceTrajetDTO[] = [];
  loading = false;
  error: string | null = null;

  constructor(private annonceService: AnnonceService) {}

  ngOnInit(): void {
    this.loadAnnonces();
  }

  loadAnnonces(): void {
    this.loading = true;
    this.error = null;

    this.annonceService.getAllAnnoncesConducteurs().subscribe({
      next: (data: AnnonceTrajetDTO[]) => {
        this.annonces = data;
        this.loading = false;
      },
      error: (error: any) => {
        this.error = 'Erreur lors du chargement des annonces';
        this.loading = false;
        console.error('Erreur:', error);
      }
    });
  }

  refreshAnnonces(): void {
    this.loadAnnonces();
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString('fr-FR');
  }

  formatEtapes(etapes: string[]): string {
    return etapes.join(' → ');
  }

  modifierAnnonce(id: number): void {
    console.log('Modifier annonce avec ID:', id);
    // TODO: Implémenter la logique de modification
    // Par exemple, naviguer vers une page de modification
    // this.router.navigate(['/modifier-annonce', id]);
  }

  supprimerAnnonce(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette annonce ?')) {
      console.log('Supprimer annonce avec ID:', id);
      // TODO: Implémenter la logique de suppression
      // this.annonceService.deleteAnnonce(id).subscribe({
      //   next: () => {
      //     this.loadAnnonces(); // Recharger la liste
      //   },
      //   error: (error) => {
      //     console.error('Erreur lors de la suppression:', error);
      //   }
      // });
    }
  }
}
