
import { Routes } from '@angular/router';
import {MesTrajet} from './components/pages/mes-trajet/mes-trajet';
import {LayoutComponent} from './components/layout/layout/layout';
export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'mes-trajet',
        component: MesTrajet
      },
      {
        path: '',
        redirectTo: '/mes-trajet',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '**',
    redirectTo: '/mes-trajet'
  }
];
