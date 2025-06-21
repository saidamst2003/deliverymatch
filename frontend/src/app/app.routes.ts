import { Routes } from '@angular/router';
import { LoginComponent } from './components/pages/login/login';
import { authGuard } from './guards/auth-guard';
import { MesTrajet } from './components/pages/mes-trajet/mes-trajet';
import { AccueilComponent } from './components/pages/accueil/accueil';

export const routes: Routes = [
  // Full-screen, public routes
  { path: 'accueil', component: AccueilComponent, data: { fullScreen: true } },
  { path: 'login', component: LoginComponent, data: { fullScreen: true } },
  { path: 'dashboard', component: MesTrajet, canActivate: [authGuard], data: { fullScreen: true } },
  { path: '', redirectTo: 'accueil', pathMatch: 'full' },
  // Redirect any unknown paths to the accueil page
  { path: '**', redirectTo: 'accueil' },
];
