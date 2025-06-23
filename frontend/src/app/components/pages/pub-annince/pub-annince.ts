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
  annonceEnCoursDeModification: any = null;
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

      // Décodage du payload JWT
      const payloadJson = atob(token.split('.')[1]);
      const payload = JSON.parse(payloadJson);

      console.log('JWT Payload:', payload);

      // Essaie de lire le rôle selon différentes clés possibles
      if (payload.role) {
        return payload.role;
      }
      if (payload.roles && Array.isArray(payload.roles) && payload.roles.length > 0) {
        return payload.roles[0];
      }
      if (payload.authorities && Array.isArray(payload.authorities) && payload.authorities.length > 0) {
        return payload.authorities[0];
      }

      // Si aucune propriété trouvée, retourne null
      return null;
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
      error: (err) => {
        this.error = 'Erreur lors du chargement des annonces';
        this.loading = false;
        console.error(err);
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

  annulerModification(): void {
    this.isEditModalVisible = false;
    this.annonceEnCoursDeModification = null;
  }

  modifierAnnonce(id: number): void {
    console.log(`[DEBUG] modifierAnnonce called with id: ${id}`);
    const annonce = this.annonces.find(a => a.id === id);

    if (annonce) {
      console.log('[DEBUG] Annonce found:', annonce);
      this.annonceEnCoursDeModification = { 
        ...annonce, 
        etapesIntermediairesString: annonce.etapesIntermediaires ? annonce.etapesIntermediaires.join(', ') : ''
      };
      this.isEditModalVisible = true;
      console.log('[DEBUG] isEditModalVisible set to true. Modal should appear.');
    } else {
      console.error(`[DEBUG] Annonce with id: ${id} not found!`);
    }
  }

  supprimerAnnonce(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette annonce ?')) {
      this.loading = true;
      this.error = null;
      this.annonceService.deleteAnnonce(id).subscribe({
        next: () => {
          this.loading = false;
          this.loadAnnonces();
        },
        error: (err) => {
          this.error = 'Erreur lors de la suppression de l\'annonce';
          this.loading = false;
          console.error(err);
        }
      });
    }
  }

  sauvegarderModification(): void {
    if (this.annonceEnCoursDeModification) {
      this.loading = true;
      this.error = null;

      // Convertir la chaîne des étapes en tableau
      this.annonceEnCoursDeModification.etapesIntermediaires =
        this.annonceEnCoursDeModification.etapesIntermediairesString
          ?.split(',')
          .map((e: string) => e.trim())
          .filter((e: string) => e.length > 0) || [];

      this.annonceService.updateAnnonce(this.annonceEnCoursDeModification.id, this.annonceEnCoursDeModification).subscribe({
        next: () => {
          this.loading = false;
          this.isEditModalVisible = false;
          this.annonceEnCoursDeModification = null;
          this.loadAnnonces();
        },
        error: (err) => {
          this.error = 'Erreur lors de la modification de l\'annonce';
          this.loading = false;
          console.error(err);
        }
      });
    }
  }

  goBack(): void {
    this.location.back();
  }
}
