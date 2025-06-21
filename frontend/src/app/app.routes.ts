import { Routes } from '@angular/router';
import { LoginComponent } from './components/pages/login/login';
import { LayoutComponent } from './components/layout/layout/layout';
import { AccueilComponent } from './components/pages/accueil/accueil';
import { MesTrajet } from './components/pages/mes-trajet/mes-trajet';
import { PubAnnince } from './components/pages/pub-annince/pub-annince';
import { AffichageAnnonce } from './components/pages/affichage-annonce/affichage-annonce';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'accueil', component: AccueilComponent, data: { fullScreen: true } },
      { path: 'login', component: LoginComponent, data: { fullScreen: true } },
      { path: 'dashboard', component: MesTrajet, canActivate: [authGuard], data: { fullScreen: true } },
      { path: 'pub', component: PubAnnince, canActivate: [authGuard], data: { fullScreen: true } },
      { path: 'affichage-annonce', component: AffichageAnnonce, data: { fullScreen: true } },
      { path: '', redirectTo: 'accueil', pathMatch: 'full' },
    ],
  },
  { path: '**', redirectTo: 'accueil' },
];
