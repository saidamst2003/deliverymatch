import { Routes } from '@angular/router';
import { LoginComponent } from './components/pages/login/login';
import { authGuard } from './guards/auth-guard';
import { MesTrajet } from './components/pages/mes-trajet/mes-trajet';

export const routes: Routes = [
  { path: 'login', component: LoginComponent, data: { fullScreen: true } },
  { path: 'dashboard', component: MesTrajet, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' },
];
