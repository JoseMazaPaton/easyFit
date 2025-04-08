import { Routes } from '@angular/router';

export const landingRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./layouts/landing-layout/landing-layout.component').then(
        (m) => m.LandingLayoutComponent
      ),
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./pages/landing-page/landing-page.component').then(
            (m) => m.LandingPageComponent
          ),
      },
    ],
  },
];
