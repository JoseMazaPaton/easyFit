import { Component } from '@angular/core';
import { Router, ActivatedRoute, UrlSegment } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { AuthService } from '../../../models/services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-login-form',
  imports: [FormsModule, CommonModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css',
})
export class LoginFormComponent {
  email: string = '';
  password: string = '';
  esAdmin: boolean = false;
  mensajeError: string | null = null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Detecta si la ruta contiene "admin"
    const rutaActual = this.route.snapshot.pathFromRoot
      .flatMap(r => r.url)
      .map((segment: UrlSegment) => segment.path);

    this.esAdmin = rutaActual.includes('admin');
    console.log('Es Admin:', this.esAdmin);
  }

  onInput(): void {
    // Limpiar el mensaje de error al escribir
    this.mensajeError = null;
  }

  login(loginForm: NgForm): void {
    if (loginForm.invalid) {
      Object.values(loginForm.controls).forEach(control => control.markAsTouched());
      return;
    }

    const { email, password } = loginForm.value;
    console.log('Intentando login con:', email);

    this.authService.login(email, password).subscribe({
      next: (response) => {
        console.log('Respuesta login:', response);
        console.log('Rol recibido:', response.rol);
        
        // Normalizar rol (asegurándonos que no es undefined)
        const rol = response.rol ? response.rol.toUpperCase() : '';
        console.log('Rol normalizado:', rol);

        // Usuario intenta acceder por ruta que no le corresponde
        if (this.esAdmin && rol !== 'ROL_ADMIN') {
          this.mensajeError = 'Usuario o contraseña incorrectos.';
          loginForm.reset();
          console.log('Acceso denegado: usuario normal intentando acceder como admin');
          return;
        }

        if (!this.esAdmin && rol === 'ROL_ADMIN') {
          this.mensajeError = 'Usuario o contraseña incorrectos.';
          loginForm.reset();
          console.log('Acceso denegado: admin intentando acceder como usuario normal');
          return;
        }

        // Guardar token y usuario
        this.authService.guardarUsuarioYToken(response.token, {
          email: response.email,
          nombre: response.nombre,
          rol: response.rol
        });
        console.log('Usuario y token guardados');

        // Verificar qué valor de rol se está recibiendo para diagnosticar el problema
        console.log('Redirigiendo según rol:', rol);

        // Redirigir según rol - Haciendo la comparación más flexible
        if (rol.includes('ADMIN')) {
          console.log('Redirigiendo a admin dashboard');
          this.router.navigate(['/admin/dashboard'])
            .then(success => console.log('Navegación completada:', success))
            .catch(error => console.error('Error en navegación:', error));
        } else {
          console.log('Redirigiendo a usuario dashboard');
          this.router.navigate(['/usuario/dashboard'])
            .then(success => console.log('Navegación completada:', success))
            .catch(error => console.error('Error en navegación:', error));
        }
      },
      error: (err) => {
        console.error('Error de login:', err);
        this.mensajeError = 'Usuario o contraseña incorrectos.';
        loginForm.reset();
      }
    });
  }
}