import { Routes } from '@angular/router';
import { AppinternaLayoutComponent } from './layouts/appinterna-layout/appinterna-layout.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { DiarioComponent } from './pages/diario/diario.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { ObjetivosComponent } from './pages/objetivos/objetivos.component';
import { AlimentosComponent } from './pages/alimentos/alimentos.component';

export const userRoutes: Routes = [
  {
    path: '',
    component: AppinternaLayoutComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent
      },
      {
        path: 'diario',
        component: DiarioComponent
      },
      {
        path: 'perfil',
        component: PerfilComponent
      },
      {
        path: 'objetivos',
        component: ObjetivosComponent
      },
      {
        path: 'alimentos',
        component: AlimentosComponent
      },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }
    ]
  }
];
