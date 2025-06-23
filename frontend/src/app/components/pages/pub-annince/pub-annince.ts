import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnonceTrajetDTO } from "../../../models/AnnonceTrajetDTO";
import { AnnonceService } from "../../../services/annonce";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Location } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-pun-announce',
  templateUrl: './pub-annince.html',
  styleUrls: ['./pub-annince.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, ReactiveFormsModule]
})
export class PubAnnince implements OnInit {
  annonces: AnnonceTrajetDTO[] = [];
  filteredAnnonces: AnnonceTrajetDTO[] = [];
  loading = false;
  error: string | null = null;
  isEditModalVisible = false;
  annonceEnCoursDeModification: any = null;
  userRole: string | null = null;
  
  searchForm: FormGroup;
  typesMarchandise = ['FRAGILE', 'LIQUIDE', 'ALIMENTAIRE', 'ELECTRONIQUE', 'VETEMENT', 'AUTRE'];

  constructor(
    private annonceService: AnnonceService, 
    private location: Location,
    private fb: FormBuilder
  ) {
    this.searchForm = this.fb.group({
      destination: [''],
      dateCreation: [''],
      typeMarchandise: ['']
    });
  }

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

  onAdvancedSearch(): void {
    const criteria = this.searchForm.value;
    this.loading = true;
    this.annonceService.searchAnnonces(criteria).subscribe({
      next: (data) => {
        this.filteredAnnonces = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors de la recherche.';
        this.loading = false;
        console.error(err);
      }
    });
  }

  resetSearch(): void {
    this.searchForm.reset({
      destination: '',
      dateCreation: '',
      typeMarchandise: ''
    });
    this.filteredAnnonces = this.annonces;
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
