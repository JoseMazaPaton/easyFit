import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-resumen-comidas-diario',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './resumen-comidas-diario.component.html',
  styleUrl: './resumen-comidas-diario.component.css'
})
export class ResumenComidasDiarioComponent {
  
  @Input() resumen!: {
    kcalObjetivo: number;
    kcalConsumidas: number;
    kcalRestantes: number;
    carbohidratosPorcentaje: number;
    grasasPorcentaje: number;
    proteinasPorcentaje: number;
  };

  // No se necesitan métodos adicionales ya que solo se aplican cambios de color basados en condiciones simples
}