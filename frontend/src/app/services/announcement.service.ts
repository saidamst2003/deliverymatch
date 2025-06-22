import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AnnonceTrajet {
  id: number;
  lieuDepart: string;
  etapesIntermediaires: string[];
  destination: string;
  capaciteDisponible: number;
  dateCreation: string;
  typeMarchandiseAcceptee: TypeMarchandise;
  conducteur: any;
  demandesTransport: any[];
}

export enum TypeMarchandise {
  ELECTRONIQUE = 'ELECTRONIQUE',
  TEXTILE = 'TEXTILE',
  ALIMENTAIRE = 'ALIMENTAIRE',
  FRAGILE = 'FRAGILE',
  DOCUMENTS = 'DOCUMENTS',
  LIVRES = 'LIVRES',
  MOBILIER = 'MOBILIER',
  MEDICAL = 'MEDICAL',
  AUTOMOBILE = 'AUTOMOBILE',
  SPORT = 'SPORT',
  AUTRE = 'AUTRE'
}

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {
  private apiUrl = 'http://localhost:8080/api'; // Adjust this to your backend URL

  constructor(private http: HttpClient) { }

  // Search announcements by criteria
  searchAnnonces(destination?: string, dateCreation?: string, typeMarchandise?: TypeMarchandise): Observable<AnnonceTrajet[]> {
    let params = new HttpParams();
    
    if (destination) {
      params = params.set('destination', destination);
    }
    if (dateCreation) {
      params = params.set('dateCreation', dateCreation);
    }
    if (typeMarchandise) {
      params = params.set('typeMarchandise', typeMarchandise);
    }

    return this.http.get<AnnonceTrajet[]>(`${this.apiUrl}/annonces/search`, { params });
  }

  // Get all driver announcements (admin access)
  getAllAnnoncesConducteurs(): Observable<AnnonceTrajet[]> {
    return this.http.get<AnnonceTrajet[]>(`${this.apiUrl}/admin/annonces-conducteurs`);
  }

  // Get all announcements
  getAllAnnonces(): Observable<AnnonceTrajet[]> {
    return this.http.get<AnnonceTrajet[]>(`${this.apiUrl}/annonces`);
  }

  // Create new announcement
  createAnnonce(annonce: Partial<AnnonceTrajet>): Observable<AnnonceTrajet> {
    return this.http.post<AnnonceTrajet>(`${this.apiUrl}/annonces`, annonce);
  }

  // Update announcement
  updateAnnonce(id: number, annonce: Partial<AnnonceTrajet>): Observable<AnnonceTrajet> {
    return this.http.put<AnnonceTrajet>(`${this.apiUrl}/annonces/${id}`, annonce);
  }

  // Delete announcement
  deleteAnnonce(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/annonces/${id}`);
  }

  // Get announcement by ID
  getAnnonceById(id: number): Observable<AnnonceTrajet> {
    return this.http.get<AnnonceTrajet>(`${this.apiUrl}/annonces/${id}`);
  }
} 