import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnonceTrajetDTO } from "../../../models/AnnonceTrajetDTO";
import { AnnonceService } from "../../../services/annonce";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-pun-announce',
  templateUrl: './pub-annince.html',
  styleUrls: ['./pub-annince.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class PubAnnince implements OnInit {
  annonces: AnnonceTrajetDTO[] = [];
  loading = false;
  error: string | null = null;
  isEditModalVisible = false;
  annonceEnCoursDeModification: AnnonceTrajetDTO | null = null;
  userRole: string | null = null;

  constructor(private annonceService: AnnonceService) {}

  ngOnInit(): void {
    this.userRole = this.getUserRole();
    this.loadAnnonces();
  }

  getUserRole(): string | null {
    const token = localStorage.getItem('token');
    if (!token) return null;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role || null;
    } catch {
      return null;
    }
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
    const annonce = this.annonces.find(a => a.id === id);
    if (annonce) {
      // Create a copy to avoid modifying the original object directly
      this.annonceEnCoursDeModification = { ...annonce };
      this.isEditModalVisible = true;
    }
  }

  sauvegarderModification(): void {
    if (this.annonceEnCoursDeModification) {
      this.loading = true;
      this.error = null;
      
      this.annonceService.updateAnnonce(this.annonceEnCoursDeModification.id, this.annonceEnCoursDeModification).subscribe({
        next: () => {
          this.loading = false;
          this.isEditModalVisible = false;
          this.annonceEnCoursDeModification = null;
          this.loadAnnonces();
        },
        error: (error: any) => {
          console.error('Erreur lors de la modification:', error);
          this.error = 'Erreur lors de la modification de l\'annonce';
          this.loading = false;
        }
      });
    }
  }

  annulerModification(): void {
    this.isEditModalVisible = false;
    this.annonceEnCoursDeModification = null;
  }

  supprimerAnnonce(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette annonce ?')) {
      this.loading = true;
      this.error = null;
      
      this.annonceService.deleteAnnonce(id).subscribe({
        next: () => {
          console.log('Annonce supprimée avec succès');
          this.loading = false;
          // Recharger la liste des annonces après suppression
          this.loadAnnonces();
        },
        error: (error: any) => {
          console.error('Erreur lors de la suppression:', error);
          this.error = 'Erreur lors de la suppression de l\'annonce';
          this.loading = false;
        }
      });
    }
  }
}
