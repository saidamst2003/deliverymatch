import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnonceTrajetDTO } from "../../../models/AnnonceTrajetDTO";
import { AnnonceService } from "../../../services/annonce";
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';
import { RouterModule } from '@angular/router';

// We'll create a new type for the form to handle the etapes as a string
interface AnnonceTrajetFormDTO extends Omit<AnnonceTrajetDTO, 'etapesIntermediaires'> {
  etapesIntermediairesString?: string;
}

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
  annonceEnCoursDeModification: AnnonceTrajetFormDTO | null = null;
  userRole: string | null = null;

  constructor(private annonceService: AnnonceService, private location: Location) {}

  ngOnInit(): void {
    this.userRole = this.getUserRole();
    console.log('User role in pub-annince:', this.userRole);
    this.loadAnnonces();
  }

  getUserRole(): string | null {
    try {
      // Check multiple possible token storage locations
      const token = localStorage.getItem('token') || 
                   localStorage.getItem('authToken') || 
                   sessionStorage.getItem('token') || 
                   sessionStorage.getItem('authToken');
      
      if (!token) {
        console.warn('Aucun token trouvé dans localStorage ou sessionStorage.');
        return null;
      }
      
      const payload = JSON.parse(atob(token.split('.')[1]));
      
      // Try multiple possible role field names, common in Spring Security
      const role = payload.role || 
                  (payload.roles && payload.roles[0]) || 
                  payload.authority || 
                  (payload.authorities && payload.authorities[0]) || 
                  null;
      
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

  modifierAnnonce(id: number): void {
    const annonce = this.annonces.find(a => a.id === id);
    if (annonce) {
      this.annonceEnCoursDeModification = { 
        ...annonce, 
        etapesIntermediairesString: annonce.etapesIntermediaires ? annonce.etapesIntermediaires.join(', ') : ''
      };
      this.isEditModalVisible = true;
    }
  }

  sauvegarderModification(): void {
    if (this.annonceEnCoursDeModification) {
      this.loading = true;
      this.error = null;
      
      // Convert string back to array
      const etapesArray = this.annonceEnCoursDeModification.etapesIntermediairesString
        ? this.annonceEnCoursDeModification.etapesIntermediairesString.split(',').map(s => s.trim()).filter(Boolean)
        : [];
        
      const annonceToUpdate: AnnonceTrajetDTO = {
          ...this.annonceEnCoursDeModification,
          etapesIntermediaires: etapesArray,
      };
      // remove the temporary string property
      delete (annonceToUpdate as any).etapesIntermediairesString;

      this.annonceService.updateAnnonce(annonceToUpdate.id, annonceToUpdate).subscribe({
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
