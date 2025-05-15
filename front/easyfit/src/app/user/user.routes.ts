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
        data: { animation: 'DashboardPage' } // 👈 aquí
      },
      {
        path: 'diario',
        loadComponent: () =>
          import('./pages/diario-user/diario-user.component').then(
            m => m.DiarioUserComponent
          ),
        data: { animation: 'DiarioPage' } // 👈 aquí
      },
      {
        path: 'diario/alimento/:idComida',
        loadComponent: () =>
          import('./components/diario-user/agregar-alimento-diario-page/agregar-alimento-diario-page.component').then(
            m => m.AgregarAlimentoDiarioPageComponent
          ),
        data: { animation: 'AgregarAlimentoPage' } // opcional, si quieres
      },
      {
        path: 'alimentos',
        loadComponent: () =>
          import('./pages/alimentos-user/alimentos-user.component').then(
            m => m.AlimentosUserComponent
          ),
        data: { animation: 'AlimentosPage' }
      },
      {
        path: 'objetivos',
        loadComponent: () =>
          import('./pages/objetivos-user/objetivos-user.component').then(
            m => m.ObjetivosUserComponent
          ),
        data: { animation: 'ObjetivosPage' }
      },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }
    ]
  }
];
