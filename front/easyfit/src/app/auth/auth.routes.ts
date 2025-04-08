import { Routes } from '@angular/router';

export const authRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./layouts/access-layout/access-layout.component').then(
        m => m.AccessLayoutComponent
      ),
    children: [
      {
        path: 'login',
        loadComponent: () =>
          import('./pages/login-form/login-form.component').then(
            m => m.LoginFormComponent
          ),
      },
      {
        path: 'register',
        loadComponent: () =>
          import('./pages/register-form/register-form.component').then(
            m => m.RegisterFormComponent
          ),
      },
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      }
    ]
  }
];
