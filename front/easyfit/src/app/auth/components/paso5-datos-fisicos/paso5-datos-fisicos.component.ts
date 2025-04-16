import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegistroService } from '../../../models/services/registro.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-paso5-datos-fisicos',
  standalone: true,
  imports: [RouterLink, CommonModule,ReactiveFormsModule],
  templateUrl: './paso5-datos-fisicos.component.html',
  styleUrl: './paso5-datos-fisicos.component.css'
})
export class Paso5DatosFisicosComponent {
  formPaso5!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private registroService: RegistroService
  ) {}

  ngOnInit(): void {
    this.formPaso5 = this.fb.group({
      altura: [null, [Validators.required, Validators.min(100), Validators.max(250)]],
      pesoActual: [null, [Validators.required, Validators.min(30), Validators.max(250)]],
      pesoObjetivo: [null, [Validators.required, Validators.min(30), Validators.max(250)]]
    });
  }

  siguientePaso() {
    if (this.formPaso5.invalid) {
      this.formPaso5.markAllAsTouched();
      return;
    }

    const { altura, pesoActual, pesoObjetivo } = this.formPaso5.value;

    this.registroService.setPaso({
      usuario: { altura },
      objetivo: {
        pesoActual,
        pesoObjetivo
      }
    });

    this.router.navigate(['/auth/register/paso6']);
  }

  checkControl(controlName: string, error: string): boolean {
    const control = this.formPaso5.get(controlName);
    return !!control && control.hasError(error) && control.touched;
  }
}