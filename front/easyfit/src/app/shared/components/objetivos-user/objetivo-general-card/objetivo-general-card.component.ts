import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ObjetivoResponse } from '../../../../models/interfaces/objetivo-response';
import { ObjetivoService } from '../../../../models/services/objetivo.service';

@Component({
  selector: 'app-objetivo-general-card',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './objetivo-general-card.component.html',
  styleUrl: './objetivo-general-card.component.css'
})
export class ObjetivoGeneralCardComponent {
  @Input() objetivo!: ObjetivoResponse;
  @Output() actualizado = new EventEmitter<void>();

  formPesoActual!: FormGroup;
  formPesoObjetivo!: FormGroup;
  formObjetivo!: FormGroup;

  opcionesObjetivo = ['PERDERPESO', 'MANTENER', 'GANARPESO'];

  // feedback visual tras guardado
  guardadoPesoActual = false;
  guardadoPesoObjetivo = false;
  guardadoObjetivo = false;

  constructor(private fb: FormBuilder, 
              private objetivoService: ObjetivoService) {}

  ngOnInit(): void {
    this.formPesoActual = this.fb.group({
      pesoActual: [this.objetivo.pesoActual, [Validators.required, Validators.min(30)]]
    });

    this.formPesoObjetivo = this.fb.group({
      pesoObjetivo: [this.objetivo.pesoObjetivo, [Validators.required, Validators.min(30)]]
    });

    this.formObjetivo = this.fb.group({
      objetivoUsuario: [this.objetivo.objetivoUsuario, Validators.required]
    });
  }

  guardarPesoActual() {
    if (this.formPesoActual.valid) {
      this.objetivoService.actualizarPesoActual(this.formPesoActual.value).subscribe({
        next: () => {
          this.resetChecks();
          this.guardadoPesoActual = true;
          setTimeout(() => this.actualizado.emit(), 500)
        },
        error: err => console.error('❌ Error al actualizar peso actual', err)
      });
    }
  }

  guardarPesoObjetivo() {
    if (this.formPesoObjetivo.valid) {
      this.objetivoService.actualizarPesoObjetivo(this.formPesoObjetivo.value).subscribe({
        next: () => {
          this.resetChecks();
          this.guardadoPesoObjetivo = true;
          setTimeout(() => this.actualizado.emit(), 500)
        },
        error: err => console.error('❌ Error al actualizar peso objetivo', err)
      });
    }
  }

  guardarObjetivo() {
    if (this.formObjetivo.valid) {
      this.objetivoService.actualizarObjetivoUsuario(this.formObjetivo.value).subscribe({
        next: () => {
          this.resetChecks();
          this.guardadoObjetivo = true;
          setTimeout(() => this.actualizado.emit(), 500)
        },
        error: err => console.error('❌ Error al actualizar objetivo', err)
      });
    }
  }

  private resetChecks(): void {
    this.guardadoPesoActual = false;
    this.guardadoPesoObjetivo = false;
    this.guardadoObjetivo = false;
  }
}
