import { Routes } from '@angular/router';

export const userRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./layouts/cliente-layout/cliente-layout.component').then(
        m => m.ClienteLayoutComponent
      ),
    children: [
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./pages/dashboard-user/dashboard-user.component').then(
            m => m.DashboardUserComponent
          ),
      },
      {
        path: 'diario',
        loadComponent: () =>
          import('./pages/diario-user/diario-user.component').then(
            m => m.DiarioUserComponent
          ),
      },
      {
        path: 'alimentos',
        loadComponent: () =>
          import('./pages/alimentos-user/alimentos-user.component').then(
            m => m.AlimentosUserComponent
          ),
      },
      {
        path: 'objetivos',
        loadComponent: () =>
          import('./pages/objetivos-user/objetivos-user.component').then(
            m => m.ObjetivosUserComponent
          ),
      },
      {
        path: 'perfil',
        loadComponent: () =>
          import('./pages/perfil-user/perfil-user.component').then(
            m => m.PerfilUserComponent
          ),
      },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }
    ]
  }
];
