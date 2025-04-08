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
  
  email: string = "";
  password: string = "";

  constructor (private router: Router,
              private authService: AuthService 
  ) {}


  login(loginForm: NgForm) {
    
    if (loginForm.invalid) {
      Object.values(loginForm.controls).forEach(control => {
        control.markAsTouched();
      });
      return;
    }

    this.email = loginForm.value.email as string;
    this.password = loginForm.value.password as string;
    this.authService.login(this.email, this.password).subscribe({
      next: (response) => {
        this.router.navigate(['/usuario']);
      },
      error: (error) => {
        alert('Usuario o Contraseña incorrectos')
        loginForm.reset();
      }
    },
  )
    
  }
}
