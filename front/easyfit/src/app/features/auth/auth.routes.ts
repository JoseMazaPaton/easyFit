import { Routes } from '@angular/router';
import { AccessLayoutComponent } from './layouts/access-layout/access-layout.component';

export const authRoutes: Routes = [
  {
    path: '',
    component: AccessLayoutComponent,
    children: [
      {
        path: 'login',
        loadComponent: () =>
          import('./pages/login-form/login-form.component').then(
            (c) => c.LoginFormComponent
          )
      },
      {
        path: 'register',
        loadComponent: () =>
          import('./pages/register-form/register-form.component').then(
            (c) => c.RegisterFormComponent
          )
      },
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'login'
      }
    ]
  }
];
