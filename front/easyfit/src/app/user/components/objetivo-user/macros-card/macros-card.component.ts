import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ObjetivoService } from '../../../../models/services/objetivo.service';
import { ValorNutricional } from '../../../../models/interfaces/valor-nutricional';

@Component({
  selector: 'app-macros-card',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './macros-card.component.html',
  styleUrl: './macros-card.component.css'
})
export class MacrosCardComponent {
  @Input() valores!: ValorNutricional;
  @Output() actualizado = new EventEmitter<void>();

  form!: FormGroup;
  valoresOpcionales = [10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80];

  constructor(private fb: FormBuilder,
              private objetivoService: ObjetivoService) {}

  ngOnInit(): void {
    if (!this.valores) return;
  
    this.form = this.fb.group({
      porcentajeProteinas: [this.valores.porcentajeProteinas, [Validators.required, Validators.min(0)]],
      porcentajeCarbohidratos: [this.valores.porcentajeCarbohidratos, [Validators.required, Validators.min(0)]],
      porcentajeGrasas: [this.valores.porcentajeGrasas, [Validators.required, Validators.min(0)]]
    });
  }
              

  get sumaTotal(): number {
    const { porcentajeProteinas, porcentajeCarbohidratos, porcentajeGrasas } = this.form.value;
    return (
      Number(porcentajeProteinas) +
      Number(porcentajeCarbohidratos) +
      Number(porcentajeGrasas)
    );
  }
              

  onSubmit(): void {
    if (this.form.invalid || this.sumaTotal !== 100) return;
  
    const dto = {
      porcentajeProteinas: Number(this.form.value.porcentajeProteinas),
      porcentajeCarbohidratos: Number(this.form.value.porcentajeCarbohidratos),
      porcentajeGrasas: Number(this.form.value.porcentajeGrasas)
    };
  
    this.objetivoService.actualizarMacros(dto).subscribe({
      next: () => this.actualizado.emit(),
      error: (err) => console.error('❌ Error al actualizar macros:', err)
    });
  }

  
}
