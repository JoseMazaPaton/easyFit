import { Routes } from '@angular/router';

export const authRoutes: Routes = [


    // ✅ ACCESO DEL USUARIO
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
        children: [
          { path: '', redirectTo: 'paso1', pathMatch: 'full' },
          {
            path: 'paso1',
            loadComponent: () =>
              import('./components/paso1-bienvenida/paso1-bienvenida.component').then(
                m => m.Paso1BienvenidaComponent
              )
          },
          {
            path: 'paso2',
            loadComponent: () =>
              import('./components/paso2-sexo-fecha/paso2-sexo-fecha.component').then(
                m => m.Paso2SexoFechaComponent
              )
          },
          {
            path: 'paso3',
            loadComponent: () =>
              import('./components/paso3-objetivo-genera/paso3-objetivo-genera.component').then(
                m => m.Paso3ObjetivoGeneraComponent
              )
          },
          {
            path: 'paso4',
            loadComponent: () =>
              import('./components/paso4-objetivo-semana/paso4-objetivo-semana.component').then(
                m => m.Paso4ObjetivoSemanaComponent
              )
          },
          {
            path: 'paso5',
            loadComponent: () =>
              import('./components/paso5-datos-fisicos/paso5-datos-fisicos.component').then(
                m => m.Paso5DatosFisicosComponent
              )
          },
          {
            path: 'paso6',
            loadComponent: () =>
              import('./components/paso6-cuenta/paso6-cuenta.component').then(
                m => m.Paso6CuentaComponent
              )
          }
        ]
      },
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      }
    ]
  },

  // ✅ ACCESO DEL ADMINISTRADOR
  {
    path: 'admin',
    loadComponent: () =>
      import('./layouts/access-admin/access-admin.component').then(
        m => m.AccessAdminComponent
      ),
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./pages/login-form/login-form.component').then(
            m => m.LoginFormComponent
          )
      }
    ]
  }
];