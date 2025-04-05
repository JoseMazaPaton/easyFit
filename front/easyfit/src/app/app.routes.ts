import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth.guard';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';

export const routes: Routes = [
    {
        path: '',
        component: MainLayoutComponent, // ← Layout con sidebar, header, etc.
        canActivate: [authGuard],       // ← Solo si está logueado
      },
      {
        path: '',
        component: AuthLayoutComponent, // ← Layout simple sin sidebar
        children: [
          {
            path: 'login',
            loadComponent: () => import('./features/auth/login/login.component')
              .then(m => m.LoginComponent)
          },
          {
            path: 'registro',
            loadComponent: () => import('./features/auth/registro/registro.component')
              .then(m => m.RegistroComponent)
          }
        ]
      },
      {
        path: '**',
        redirectTo: 'login'
      }
];
