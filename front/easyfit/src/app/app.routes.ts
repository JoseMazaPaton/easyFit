import { Routes } from '@angular/router';
import { authRoutes } from './features/auth/auth.routes';
import { landingRoutes } from './features/landing/landing.routes';
import { userRoutes } from './features/user/user.routes';

export const routes: Routes = [
  { path: 'auth', children: authRoutes },
  { path: '', children: landingRoutes },
  { path: 'usuario', children: userRoutes },

  { path: '**', redirectTo: '' },
];
