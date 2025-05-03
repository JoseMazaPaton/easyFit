import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ObjetivoResponse } from '../../../../models/interfaces/objetivo-response';
import { ObjetivoService } from '../../../../models/services/objetivo.service';

@Component({
  selector: 'app-actividad-objetivo-card',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './actividad-objetivo-card.component.html',
  styleUrl: './actividad-objetivo-card.component.css'
})
export class ActividadObjetivoCardComponent {
  @Input() objetivo!: ObjetivoResponse;
  @Output() actualizado = new EventEmitter<void>();

  formActividad!: FormGroup;
  formMetaSemanal!: FormGroup;

  guardadoActividad = false;
  guardadoMeta = false;

  nivelesActividad = ['SEDENTARIO',
    'LIGERO',
    'MODERADO',
    'ACTIVO',
    'MUYACTIVO'];
  opcionesPeso = ['LIGERO', 'MODERADO', 'INTENSO', 'MANTENER'];

  constructor(private fb: FormBuilder, private objetivoService: ObjetivoService) {}

  ngOnInit(): void {
    this.formActividad = this.fb.group({
      actividad: [this.objetivo.actividad, Validators.required]
    });

    this.formMetaSemanal = this.fb.group({
      opcionPeso: [this.objetivo.opcionPeso, Validators.required]
    });
  }

  guardarActividad() {
    if (this.formActividad.valid) {
      this.objetivoService.actualizarActividad(this.formActividad.value).subscribe({
        next: () => {
          this.guardadoActividad = true,
          setTimeout(() => this.actualizado.emit(), 500)
        },
        error: err => console.error('❌ Error al actualizar actividad', err)
      });
    }
  }

  guardarMeta() {
    if (this.formMetaSemanal.valid) {
      this.objetivoService.actualizarMetaSemanal(this.formMetaSemanal.value).subscribe({
        next: () => {
          this.guardadoMeta = true,
          setTimeout(() => this.actualizado.emit(), 500)
        },
        error: err => console.error('❌ Error al actualizar meta semanal', err)
      });
    }
  }
}
