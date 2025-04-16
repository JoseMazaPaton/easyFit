import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegistroService } from '../../../models/services/registro.service';
import { CommonModule } from '@angular/common';
import { IRegistroUsuario } from '../../../models/interfaces/IRegistroUsuario';

@Component({
  selector: 'app-paso4-objetivo-semana',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './paso4-objetivo-semana.component.html',
  styleUrl: './paso4-objetivo-semana.component.css'
})
export class Paso4ObjetivoSemanaComponent {
  formPaso4!: FormGroup;
  opciones: { valor: string; texto: string }[] = [];
  objetivoPrincipal: 'PERDERPESO' | 'GANARPESO' | 'MANTENER' | '' = '';
  mostrarOpciones = true;

  constructor(
    private fb: FormBuilder,
    private registroService: RegistroService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.formPaso4 = this.fb.group({
      opcionPeso: ['', Validators.required]
    });

    const datos: IRegistroUsuario = this.registroService.getDatos();
    this.objetivoPrincipal = datos.objetivo?.objetivoUsuario ?? '';

    if (this.objetivoPrincipal === 'MANTENER') {
      this.mostrarOpciones = false;
      this.registroService.setPaso({ objetivo: { opcionPeso: 'MANTENER' } });
      this.router.navigate(['/auth/register/paso5']);
      return;
    }

    const objetivoTexto = this.objetivoPrincipal === 'PERDERPESO' ? 'Perder peso' : 'Ganar peso';

    this.opciones = [
      { valor: 'LIGERO', texto: `${objetivoTexto} ligeramente` },
      { valor: 'MODERADO', texto: `${objetivoTexto} moderadamente` },
      { valor: 'INTENSO', texto: `${objetivoTexto} intensamente` }
    ];
  }

  seleccionarObjetivo(valor: string) {
    this.formPaso4.patchValue({ opcionPeso: valor });
  }

  checkControl(controlName: string, error: string): boolean {
    const control = this.formPaso4.get(controlName);
    return !!control && control.hasError(error) && control.touched;
  }

  siguientePaso() {
    if (this.formPaso4.invalid && this.mostrarOpciones) {
      this.formPaso4.markAllAsTouched();
      return;
    }

    const opcion = this.mostrarOpciones ? this.formPaso4.value.opcionPeso : 'MANTENER';
    this.registroService.setPaso({ objetivo: { opcionPeso: opcion } });
    this.router.navigate(['/auth/register/paso5']);
  }
}