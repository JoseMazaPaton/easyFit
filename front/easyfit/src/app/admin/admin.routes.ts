import { CategoriasAdminComponent } from './pages/categorias-admin/categorias-admin.component';
import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';

export const adminRoutes: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./pages/dashboard-admin/dashboard-admin.component')
            .then(m => m.DashboardAdminComponent)
      },
      {
        path: 'usuarios',
        loadComponent: () =>
          import('./pages/usuarios-admin/usuarios-admin.component')
            .then(m => m.UsuariosAdminComponent)
      },
      {
        path: 'alimentos',
        loadComponent: () =>
          import('./pages/alimentos-admin/alimentos-admin.component')
            .then(m => m.AlimentosAdminComponent)
      },
      {
        path: 'categorias',
        loadComponent: () =>
          import('./pages/categorias-admin/categorias-admin.component')
            .then(m => m.CategoriasAdminComponent)
      },
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'dashboard'
      }
    ]
  }
];