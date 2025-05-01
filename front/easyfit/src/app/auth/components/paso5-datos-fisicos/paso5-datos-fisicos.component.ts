import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule, ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
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

  // ngOnInit(): void {
  //   this.formPaso5 = this.fb.group({
  //     altura: [null, [Validators.required, Validators.min(100), Validators.max(250)]],
  //     pesoActual: [null, [Validators.required, Validators.min(30), Validators.max(250)]],
  //     pesoObjetivo: [null, [Validators.required, Validators.min(30), Validators.max(250)]]
  //   });
  // }
  ngOnInit(): void {
    this.formPaso5 = this.fb.group({
      altura: [null, [Validators.required, Validators.min(100), Validators.max(250)]],
      pesoActual: [null, [Validators.required, Validators.min(30), Validators.max(250)]],
      pesoObjetivo: [null, [Validators.required, Validators.min(30), Validators.max(250)]]
    }, {
      validators: this.validarRelacionPeso()
    });
  }

  validarRelacionPeso(): ValidatorFn {
    return (group: AbstractControl): ValidationErrors | null => {
      const pesoActual = group.get('pesoActual')?.value;
      const pesoObjetivo = group.get('pesoObjetivo')?.value;
  
      const objetivoUsuario = this.registroService.getDatos()?.objetivo?.objetivoUsuario;
  
      if (!pesoActual || !pesoObjetivo || !objetivoUsuario) return null;
  
      switch (objetivoUsuario) {
        case 'PERDERPESO':
          return pesoActual > pesoObjetivo ? null : { pesoIncoherente: 'Tu peso objetivo debe ser menor peso que el actual.' };
        case 'GANARPESO':
          return pesoActual < pesoObjetivo ? null : { pesoIncoherente: 'Tu peso objetivo debe ser mayor peso que el actual' };
        case 'MANTENER':
          return Math.abs(pesoActual - pesoObjetivo) <= 1 ? null : { pesoIncoherente: 'Deben ser iguales' };
        default:
          return null;
      }
    };
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