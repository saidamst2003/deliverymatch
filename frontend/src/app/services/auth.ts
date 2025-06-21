import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';
import { LoginRequest, LoginResponse, User } from '../models/user.model';
import { jwtDecode } from 'jwt-decode';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080'; // Ajustez selon votre backend

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  private tokenSubject = new BehaviorSubject<string | null>(null);
  public token$ = this.tokenSubject.asObservable();

  constructor() {
    // Vérifier s'il y a un token stocké au démarrage
    const token = this.getStoredToken();
    if (token && !this.isTokenExpired(token)) {
      this.tokenSubject.next(token);
      this.setCurrentUserFromToken(token);
    }
  }

  login(loginData: LoginRequest): Observable<LoginResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post<LoginResponse>(`${this.apiUrl}/user/login`, loginData, { headers })
      .pipe(
        tap(response => {
          if (response.token) {
            this.storeToken(response.token);
            this.tokenSubject.next(response.token);
            this.setCurrentUserFromToken(response.token);
          }
        })
      );
  }

  logout(): void {
    this.removeStoredToken();
    this.tokenSubject.next(null);
    this.currentUserSubject.next(null);
  }

  isAuthenticated(): boolean {
    const token = this.getStoredToken();
    return token !== null && !this.isTokenExpired(token);
  }

  getToken(): string | null {
    return this.getStoredToken();
  }

  private setCurrentUserFromToken(token: string): void {
    try {
      const decodedToken: any = jwtDecode(token);
      const user: User = {
        id: decodedToken.id,
        fullName: decodedToken.firstName,
        email: decodedToken.email || decodedToken.sub,
        role: decodedToken.role
      };
      this.currentUserSubject.next(user);
    } catch (error) {
      console.error('Error decoding token:', error);
    }
  }

  private isTokenExpired(token: string): boolean {
    try {
      const decodedToken: any = jwtDecode(token);
      const expirationDate = new Date(decodedToken.exp * 1000);
      return expirationDate < new Date();
    } catch (error) {
      return true;
    }
  }

  private storeToken(token: string): void {
    sessionStorage.setItem('auth_token', token);
  }

  getStoredToken(): string | null {
    if (typeof sessionStorage !== 'undefined') {
      return sessionStorage.getItem('auth_token');
    }
    return null;
  }

  private removeStoredToken(): void {
    sessionStorage.removeItem('auth_token');
  }
}
