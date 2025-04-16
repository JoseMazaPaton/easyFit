import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegistroService } from '../../../models/services/registro.service';

@Component({
  selector: 'app-paso3-objetivo-genera',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, CommonModule, ],
  templateUrl: './paso3-objetivo-genera.component.html',
  styleUrl: './paso3-objetivo-genera.component.css'
})

export class Paso3ObjetivoGeneraComponent {
  formPaso3!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private registroService: RegistroService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.formPaso3 = this.fb.group({
      objetivoUsuario: ['', Validators.required],
      actividad: ['', Validators.required]
    });
  }

  siguientePaso(): void {
    if (this.formPaso3.invalid) {
      this.formPaso3.markAllAsTouched();
      console.log('Formulario inválido', this.formPaso3.value);
      return;
    }

    const { objetivoUsuario, actividad } = this.formPaso3.value;

    this.registroService.setPaso({
      objetivo: { objetivoUsuario, actividad }
    });

    this.router.navigate(['/auth/register/paso4']);
  }

  checkControl(controlName: string, error: string): boolean {
    const control = this.formPaso3.get(controlName);
    return !!control && control.hasError(error) && control.touched;
  }
}