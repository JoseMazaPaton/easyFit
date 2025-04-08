import { Routes } from '@angular/router';
import { authRoutes } from './auth/auth.routes';
import { adminRoutes } from './admin/admin.routes';
import { userRoutes } from './user/user.routes';
import { landingRoutes } from './public/landing.routes';

export const routes: Routes = [
  { path: 'admin', children: adminRoutes },
  { path: 'usuario', children: userRoutes },
  { path: 'auth', children: authRoutes },
  { path: '', children: landingRoutes },
  { path: '**', redirectTo: '' },

];