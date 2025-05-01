import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-resumen-comidas-diario',
  standalone: true,
  imports: [],
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

  // Ya no necesitamos inicializar el resumen aquí, lo recibiremos como Input
}