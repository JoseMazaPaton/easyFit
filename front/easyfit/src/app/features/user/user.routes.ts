import { Routes } from '@angular/router';
import { AppInternaLayoutComponent } from './layouts/app-interna-layout/app-interna-layout.component';
import { DashboardAdminComponent } from './pages/admin/dashboard-admin/dashboard-admin.component';
import { AlimentosAdminComponent } from './pages/admin/alimentos-admin/alimentos-admin.component';
import { UsuariosAdminComponent } from './pages/admin/usuarios-admin/usuarios-admin.component';
import { DashboardUserComponent } from './pages/user/dashboard-user/dashboard-user.component';
import { AlimentosUserComponent } from './pages/user/alimentos-user/alimentos-user.component';
import { DiarioUserComponent } from './pages/user/diario-user/diario-user.component';
import { ObjetivosUserComponent } from './pages/user/objetivos-user/objetivos-user.component';
import { PerfilUserComponent } from './pages/user/perfil-user/perfil-user.component';

export const userRoutes: Routes = [
  {
    // Cuando la ruta padre sea '/usuario', entra aquí
    path: '',
    component: AppInternaLayoutComponent,
    children: [
      // Rutas para la sección "admin"
      {
        path: 'admin',
        children: [
          {
            path: 'dashboard',
            component: DashboardAdminComponent
          },
          {
            path: 'alimentos',
            component: AlimentosAdminComponent
          },
          {
            path: 'usuarios',
            component: UsuariosAdminComponent
          },
          // Cuando sea '/usuario/admin' sin nada más, redireccionamos
          {
            path: '',
            redirectTo: 'dashboard',
            pathMatch: 'full'
          }
        ]
      },

      // Rutas para la sección "user"
      {
        path: 'user',
        children: [
          {
            path: 'dashboard',
            component: DashboardUserComponent
          },
          {
            path: 'alimentos',
            component: AlimentosUserComponent
          },
          {
            path: 'diario',
            component: DiarioUserComponent
          },
          {
            path: 'objetivos',
            component: ObjetivosUserComponent
          },
          {
            path: 'perfil',
            component: PerfilUserComponent
          },
          // Cuando sea '/usuario/user' sin nada más, redireccionamos
          {
            path: '',
            redirectTo: 'dashboard',
            pathMatch: 'full'
          }
        ]
      },

      // Si alguien ingresa solo a '/usuario', redireccionamos a la sección 'user'
      {
        path: '',
        redirectTo: 'user',
        pathMatch: 'full'
      }
    ]
  }
];
