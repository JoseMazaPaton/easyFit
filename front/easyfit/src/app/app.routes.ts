import { Routes } from '@angular/router';
import { authRoutes } from './auth/auth.routes';
import { adminRoutes } from './admin/admin.routes';
import { userRoutes } from './user/user.routes';
import { landingRoutes } from './public/landing.routes';
import { adminGuard } from './core/guards/admin.guard';
import { clienteGuard } from './core/guards/cliente.guard';

export const routes: Routes = [
  { 
    path: 'admin', 
    children: adminRoutes,
    canActivate: [adminGuard]
  },
  { 
    path: 'usuario', 
    children: userRoutes,
    canActivate: [clienteGuard]
  },
  { path: 'auth', children: authRoutes },
  { path: '', children: landingRoutes },
  { path: '**', redirectTo: '' },
];