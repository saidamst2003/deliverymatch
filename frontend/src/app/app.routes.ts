import { Routes } from '@angular/router';
import {LoginComponent} from './components/pages/login/login';
import {Home} from './components/pages/home/home';
import {authGuard} from './guards/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: Home, canActivate: [authGuard] },
  { path: '**', redirectTo: '/login' }
]
