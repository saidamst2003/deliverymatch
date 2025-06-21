import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AnnouncementService, AnnonceTrajet, TypeMarchandise } from '../../../services/announcement.service';

@Component({
  selector: 'app-pub-annince',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './pub-annince.html',
  styleUrl: './pub-annince.css'
})
export class PubAnnince implements OnInit {
  announcements: AnnonceTrajet[] = [];
  filteredAnnouncements: AnnonceTrajet[] = [];
  announcementForm: FormGroup;
  searchForm: FormGroup;
  isEditing = false;
  editingId: number | null = null;
  loading = false;
  errorMessage = '';
  successMessage = '';

  // Search filters
  searchDestination = '';
  searchDateCreation = '';
  searchTypeMarchandise = '';

  // TypeMarchandise enum for template
  TypeMarchandise = TypeMarchandise;
  typeMarchandiseOptions = Object.values(TypeMarchandise);

  constructor(
    private announcementService: AnnouncementService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.announcementForm = this.fb.group({
      lieuDepart: ['', Validators.required],
      destination: ['', Validators.required],
      capaciteDisponible: ['', [Validators.required, Validators.min(0)]],
      typeMarchandiseAcceptee: ['', Validators.required],
      etapesIntermediaires: ['']
    });

    this.searchForm = this.fb.group({
      destination: [''],
      dateCreation: [''],
      typeMarchandise: ['']
    });
  }

  ngOnInit(): void {
    this.loadAnnouncements();
  }

  loadAnnouncements(): void {
    this.loading = true;
    this.announcementService.getAllAnnonces().subscribe({
      next: (data) => {
        this.announcements = data;
        this.filteredAnnouncements = data;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du chargement des annonces';
        this.loading = false;
        console.error('Error loading announcements:', error);
      }
    });
  }

  searchAnnouncements(): void {
    const searchData = this.searchForm.value;
    this.loading = true;

    this.announcementService.searchAnnonces(
      searchData.destination || undefined,
      searchData.dateCreation || undefined,
      searchData.typeMarchandise || undefined
    ).subscribe({
      next: (data) => {
        this.filteredAnnouncements = data;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors de la recherche';
        this.loading = false;
        console.error('Error searching announcements:', error);
      }
    });
  }

  clearSearch(): void {
    this.searchForm.reset();
    this.filteredAnnouncements = this.announcements;
  }

  onSubmit(): void {
    if (this.announcementForm.valid) {
      this.loading = true;
      const formData = this.announcementForm.value;
      
      // Convert etapesIntermediaires string to array
      if (formData.etapesIntermediaires) {
        formData.etapesIntermediaires = formData.etapesIntermediaires.split(',').map((etape: string) => etape.trim());
      }

      if (this.isEditing && this.editingId) {
        // Update existing announcement
        this.announcementService.updateAnnonce(this.editingId, formData).subscribe({
          next: () => {
            this.successMessage = 'Annonce mise à jour avec succès';
            this.resetForm();
            this.loadAnnouncements();
            // Navigate to affichage-annonce page after successful update
            setTimeout(() => {
              this.router.navigate(['/affichage-annonce']);
            }, 2000);
          },
          error: (error) => {
            this.errorMessage = 'Erreur lors de la mise à jour';
            this.loading = false;
            console.error('Error updating announcement:', error);
          }
        });
      } else {
        // Create new announcement
        this.announcementService.createAnnonce(formData).subscribe({
          next: () => {
            this.successMessage = 'Annonce créée avec succès ! Redirection vers la page d\'affichage...';
            this.resetForm();
            this.loadAnnouncements();
            // Navigate to affichage-annonce page after successful creation
            setTimeout(() => {
              this.router.navigate(['/affichage-annonce']);
            }, 2000);
          },
          error: (error) => {
            this.errorMessage = 'Erreur lors de la création';
            this.loading = false;
            console.error('Error creating announcement:', error);
          }
        });
      }
    }
  }

  // Navigate to affichage-annonce page
  goToAffichageAnnonce(): void {
    this.router.navigate(['/affichage-annonce']);
  }

  editAnnouncement(announcement: AnnonceTrajet): void {
    this.isEditing = true;
    this.editingId = announcement.id;
    this.announcementForm.patchValue({
      lieuDepart: announcement.lieuDepart,
      destination: announcement.destination,
      capaciteDisponible: announcement.capaciteDisponible,
      typeMarchandiseAcceptee: announcement.typeMarchandiseAcceptee,
      etapesIntermediaires: announcement.etapesIntermediaires?.join(', ')
    });
  }

  deleteAnnouncement(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette annonce ?')) {
      this.loading = true;
      this.announcementService.deleteAnnonce(id).subscribe({
        next: () => {
          this.successMessage = 'Annonce supprimée avec succès';
          this.loadAnnouncements();
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la suppression';
          this.loading = false;
          console.error('Error deleting announcement:', error);
        }
      });
    }
  }

  cancelEdit(): void {
    this.resetForm();
  }

  private resetForm(): void {
    this.announcementForm.reset();
    this.isEditing = false;
    this.editingId = null;
    this.loading = false;
    this.errorMessage = '';
    setTimeout(() => {
      this.successMessage = '';
    }, 3000);
  }

  getTypeMarchandiseLabel(type: TypeMarchandise): string {
    const labels: { [key in TypeMarchandise]: string } = {
      [TypeMarchandise.ELECTRONIQUE]: 'Électronique',
      [TypeMarchandise.TEXTILE]: 'Textile et vêtements',
      [TypeMarchandise.ALIMENTAIRE]: 'Produits alimentaires',
      [TypeMarchandise.FRAGILE]: 'Objets fragiles',
      [TypeMarchandise.DOCUMENTS]: 'Documents et papiers',
      [TypeMarchandise.LIVRES]: 'Livres et publications',
      [TypeMarchandise.MOBILIER]: 'Mobilier et décoration',
      [TypeMarchandise.MEDICAL]: 'Matériel médical',
      [TypeMarchandise.AUTOMOBILE]: 'Pièces automobiles',
      [TypeMarchandise.SPORT]: 'Équipement sportif',
      [TypeMarchandise.AUTRE]: 'Autre type'
    };
    return labels[type] || type;
  }
}
