import { Component } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegistroService } from '../../../models/services/registro.service';
import { CommonModule } from '@angular/common';
import { catchError, map, Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-paso1-bienvenida',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './paso1-bienvenida.component.html',
  styleUrl: './paso1-bienvenida.component.css'
})
export class Paso1BienvenidaComponent {

  
  formPaso1!: FormGroup;
  authService: any;

  constructor(
    private fb: FormBuilder, 
    private registroService: RegistroService, 
    private router: Router,
    private http: HttpClient)
  {}

  ngOnInit() {
    this.formPaso1 = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(4)]],
      email: [
        '', 
        [Validators.required, Validators.email],
        [this.emailDisponibleValidator()] // 👈 Asíncrono
      ]
    });
  }
  
  siguientePaso() {
    if (this.formPaso1.valid) {
      const { nombre, email } = this.formPaso1.value;
      this.registroService.setPaso({ usuario: { nombre, email } });
      this.router.navigate(['/auth/register/paso2']);
    }
  }
  
  checkControl(controlName: string, error: string): boolean {
    const control = this.formPaso1.get(controlName);
    return !!control && control.hasError(error) && (control.dirty || control.touched);
  }
  
  emailDisponibleValidator(): AsyncValidatorFn {
    return (control: AbstractControl) => {
      const email = control.value;
      if (!email) return of(null);
  
      return this.http.get<{ disponible: boolean }>(`http://localhost:9008/auth/comprobaremail?email=${email}`).pipe(
        map(res => res.disponible ? null : { emailOcupado: true }),
        catchError(() => of(null)) // no bloquea si falla
      );
    };
  }
  
}
