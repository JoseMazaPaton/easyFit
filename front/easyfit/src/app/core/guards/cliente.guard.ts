import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../models/services/auth.service';

export const clienteGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.estaLogueado() && authService.obtenerRol() === 'CLIENTE') return true;

  router.navigate(['']);
  return false;
};