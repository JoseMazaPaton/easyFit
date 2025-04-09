import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { AuthService } from '../../../models/services/auth.service';


@Component({
  standalone: true,
  selector: 'app-login-form',
  imports: [FormsModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css',
})
export class LoginFormComponent {

  email: string = '';
  password: string = '';

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  login(loginForm: NgForm): void {
    if (loginForm.invalid) {
      Object.values(loginForm.controls).forEach(control => {
        control.markAsTouched();
      });
      return;
    }

    const { email, password } = loginForm.value;

    this.authService.login(email, password).subscribe({
      next: (response) => {
        // 💾 Guardamos el token y el usuario
        this.authService.guardarUsuarioYToken(response.token, {
          email: response.email,
          nombre: response.nombre,
          rol: response.rol // Usa 'rol' o 'tipoRol' según lo que devuelve tu backend
        });

        // ✅ Redirigimos al área privada
        this.router.navigate(['/usuario']);
      },
      error: () => {
        alert('Usuario o Contraseña incorrectos');
        loginForm.reset();
      }
    });
  }

}