import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

// Types et interfaces
export interface AnnonceTrajetDTO {
  lieuDepart: string;
  etapesIntermediaires: string[];
  destination: string;
  capaciteDisponible: number;
  dateCreation: string;
  typeMarchandiseAcceptee?: TypeMarchandise;
}

export enum TypeMarchandise {
  FRAGILE = 'FRAGILE',
  LIQUIDE = 'LIQUIDE',
  ALIMENTAIRE = 'ALIMENTAIRE',
  ELECTRONIQUE = 'ELECTRONIQUE',
  VETEMENT = 'VETEMENT',
  AUTRE = 'AUTRE'
}
@Component({
  selector: 'app-mes-trajet',
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './mes-trajet.html',
  styleUrl: './mes-trajet.css'
})
export class MesTrajet implements OnInit {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);

  annonceForm: FormGroup;
  isLoading = false;
  successMessage = '';
  errorMessage = '';

  // URL de votre API backend
  private readonly API_URL = 'http://localhost:8080/api/trajets'; // Ajustez selon votre configuration

  typesMarketing = [
    { value: TypeMarchandise.FRAGILE, label: 'Fragile' },
    { value: TypeMarchandise.LIQUIDE, label: 'Liquide' },
    { value: TypeMarchandise.ALIMENTAIRE, label: 'Alimentaire' },
    { value: TypeMarchandise.ELECTRONIQUE, label: 'Électronique' },
    { value: TypeMarchandise.VETEMENT, label: 'Vêtement' },
    { value: TypeMarchandise.AUTRE, label: 'Autre' }
  ];

  constructor() {
    this.annonceForm = this.fb.group({
      conducteurId: ['', [Validators.required, Validators.min(1)]],
      lieuDepart: ['', [Validators.required, Validators.minLength(2)]],
      destination: ['', [Validators.required, Validators.minLength(2)]],
      etapesIntermediaires: this.fb.array([]),
      capaciteDisponible: ['', [Validators.required, Validators.min(0.1)]],
      typeMarchandiseAcceptee: [''],
      dateCreation: [new Date().toISOString().split('T')[0]]
    });
  }



  get etapesIntermediaires(): FormArray {
    return this.annonceForm.get('etapesIntermediaires') as FormArray;
  }

  addEtape() {
    this.etapesIntermediaires.push(this.fb.control(''));
  }

  removeEtape(index: number) {
    this.etapesIntermediaires.removeAt(index);
  }

  onSubmit() {
    if (this.annonceForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';
      this.successMessage = '';

      const conducteurId = this.annonceForm.get('conducteurId')?.value;
      const formData = {...this.annonceForm.value};

      // Supprimer conducteurId du body car il sera dans l'URL
      delete formData.conducteurId;

      // Filtrer les étapes vides
      formData.etapesIntermediaires = formData.etapesIntermediaires.filter((etape: string) => etape.trim() !== '');

      const annonceDTO: AnnonceTrajetDTO = {
        lieuDepart: formData.lieuDepart,
        etapesIntermediaires: formData.etapesIntermediaires,
        destination: formData.destination,
        capaciteDisponible: parseFloat(formData.capaciteDisponible),
        dateCreation: formData.dateCreation,
        typeMarchandiseAcceptee: formData.typeMarchandiseAcceptee || undefined
      };

      this.http.post(`${this.API_URL}/publier/${conducteurId}`, annonceDTO)
        .subscribe({
          next: (response) => {
            this.isLoading = false;
            this.successMessage = 'Annonce publiée avec succès !';
            console.log('Annonce créée:', response);

            // Réinitialiser le formulaire après succès
            setTimeout(() => {
              this.resetForm();
              this.successMessage = '';
            }, 3000);
          },
          error: (error) => {
            this.isLoading = false;
            this.errorMessage = error.error?.message || error.error || 'Erreur lors de la publication de l\'annonce';
            console.error('Erreur:', error);
          }
        });
    } else {
      // Marquer tous les champs comme touchés pour afficher les erreurs
      Object.keys(this.annonceForm.controls).forEach(key => {
        this.annonceForm.get(key)?.markAsTouched();
      });
    }



  }


  resetForm() {
    this.annonceForm.reset({
      dateCreation: new Date().toISOString().split('T')[0]
    });
    this.etapesIntermediaires.clear();
    this.successMessage = '';
    this.errorMessage = '';
  }

  goBack() {
    this.router.navigate(['/']); // Ajustez selon votre routing
  }
}
