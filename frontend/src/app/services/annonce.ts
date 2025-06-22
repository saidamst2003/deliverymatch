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
}
