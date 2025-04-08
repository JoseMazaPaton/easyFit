import { Routes } from '@angular/router';
import { authRoutes } from './features/auth/auth.routes';
import { landingRoutes } from './features/landing/landing.routes';

export const routes: Routes = [
  { path: 'auth', children: authRoutes },
  { path: '', children: landingRoutes },

  { path: '**', redirectTo: '' },
];
