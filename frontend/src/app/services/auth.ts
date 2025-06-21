import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/user/login';
  private http = inject(HttpClient);

  login(data: { email: string; password: string }) {
    return this.http.post<any>(this.apiUrl, data);
  }

  saveToken(token: string) {
    localStorage.setItem('jwt_token', token);
  }

  getToken() {
    return localStorage.getItem('jwt_token');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  logout() {
    localStorage.removeItem('jwt_token');
  }
}
