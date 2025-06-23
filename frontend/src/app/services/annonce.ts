// 2. Service Angular
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AnnonceTrajetDTO} from '../models/AnnonceTrajetDTO';

@Injectable({
  providedIn: 'root'
})
export class AnnonceService {
  private baseUrl = 'http://localhost:8081/api'; // Ajustez selon votre configuration

  constructor(private http: HttpClient) {}

  getAllAnnoncesConducteurs(): Observable<AnnonceTrajetDTO[]> {
    return this.http.get<AnnonceTrajetDTO[]>(`${this.baseUrl}/annonces-trajet/admin/annonces-conducteurs`);
  }

  deleteAnnonce(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/annonces-trajet/admin/annonces-conducteurs/${id}`);
  }

  updateAnnonce(id: number, annonce: AnnonceTrajetDTO): Observable<AnnonceTrajetDTO> {
    const url = `http://localhost:8081/api/annonces-trajet/admin/annonces-conducteurs/${id}`;
    return this.http.put<AnnonceTrajetDTO>(url, annonce);
  }

  searchAnnonces(criteria: { destination?: string; dateCreation?: string; typeMarchandise?: string }): Observable<AnnonceTrajetDTO[]> {
    let params: { [key: string]: string } = {};

    if (criteria.destination) {
      params['destination'] = criteria.destination;
    }
    if (criteria.dateCreation) {
      params['dateCreation'] = criteria.dateCreation;
    }
    if (criteria.typeMarchandise) {
      params['typeMarchandise'] = criteria.typeMarchandise;
    }

    return this.http.get<AnnonceTrajetDTO[]>(`${this.baseUrl}/annonces-trajet/search`, { params });
  }
}
