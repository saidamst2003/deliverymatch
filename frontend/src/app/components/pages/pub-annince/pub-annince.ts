import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnonceTrajetDTO } from "../../../models/AnnonceTrajetDTO";
import { AnnonceService } from "../../../services/annonce";
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-pun-announce',
  templateUrl: './pub-annince.html',
  styleUrls: ['./pub-annince.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule]
})
export class PubAnnince implements OnInit {
  annonces: AnnonceTrajetDTO[] = [];
  filteredAnnonces: AnnonceTrajetDTO[] = [];
  searchTerm: string = '';
  loading = false;
  error: string | null = null;
  isEditModalVisible = false;
  annonceEnCoursDeModification: AnnonceTrajetDTO | null = null;
  userRole: string | null = null;

  constructor(private annonceService: AnnonceService, private location: Location) {}

  ngOnInit(): void {
    this.userRole = this.getUserRole();
    console.log('User role in pub-annince:', this.userRole);
    this.loadAnnonces();
}
  getUserRole(): string | null {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        console.warn('Token non trouvé dans localStorage.');
        return null;
      }
      const payload = JSON.parse(atob(token.split('.')[1]));
      console.log('JWT Payload in pub-annince:', payload);
      const role = payload.role || payload.roles?.[0] || null;
      console.log('Extracted role in pub-annince:', role);
      return role;
    } catch (e) {
      console.error('Erreur lors du décodage du token:', e);
      return null;
    }
  }

  loadAnnonces(): void {
    this.loading = true;
    this.error = null;

    this.annonceService.getAllAnnoncesConducteurs().subscribe({
      next: (data: AnnonceTrajetDTO[]) => {
        this.annonces = data;
        this.filteredAnnonces = data;
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

  onSearch(): void {
    const term = this.searchTerm.toLowerCase();
    if (!term) {
      this.filteredAnnonces = this.annonces;
      return;
    }

    this.filteredAnnonces = this.annonces.filter(annonce => {
      const etapes = Array.isArray(annonce.etapesIntermediaires) ? annonce.etapesIntermediaires.join(' ').toLowerCase() : '';
      return (
        annonce.lieuDepart.toLowerCase().includes(term) ||
        annonce.destination.toLowerCase().includes(term) ||
        etapes.includes(term)
      );
    });
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString('fr-FR');
  }

  formatEtapes(etapes: string[]): string {
    return etapes.join(' → ');
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

  modifierAnnonce(id: number): void {
    console.log('Modifier annonce avec ID:', id);

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




  goBack(): void {
    this.location.back();
  }
}


