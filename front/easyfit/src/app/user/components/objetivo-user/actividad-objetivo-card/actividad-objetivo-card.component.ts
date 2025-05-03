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

  nivelesActividad = ['SEDENTARIO', 'LIGERO', 'MODERADO', 'ACTIVO', 'MUYACTIVO'];
  opcionesPeso = ['LIGERO', 'MODERADO', 'INTENSO', 'MANTENER'];

  constructor(private fb: FormBuilder, private objetivoService: ObjetivoService) {}

  ngOnInit(): void {
    this.formActividad = this.fb.group({
      actividad: [this.objetivo.actividad, Validators.required]
    });

    this.formMetaSemanal = this.fb.group({
      opcionPeso: [this.objetivo.opcionPeso, Validators.required]
    });

    this.suscribirCambios();
  }

  private suscribirCambios(): void {
    this.formActividad.valueChanges.subscribe(value => {
      if (this.formActividad.valid) {
        this.objetivoService.actualizarActividad(value).subscribe({
          next: () => this.actualizado.emit(),
          error: err => console.error('❌ Error al actualizar actividad', err)
        });
      }
    });

    this.formMetaSemanal.valueChanges.subscribe(value => {
      if (this.formMetaSemanal.valid) {
        this.objetivoService.actualizarMetaSemanal(value).subscribe({
          next: () => this.actualizado.emit(),
          error: err => console.error('❌ Error al actualizar meta semanal', err)
        });
      }
    });
  }
}
