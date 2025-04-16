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
    kcalObjetivo: number ;
    kcalConsumidas: number;
    kcalRestantes: number;
    carbohidratosPorcentaje: number;
    grasasPorcentaje: number;
    proteinasPorcentaje: number;
  };

  constructor() {
    this.resumen = {
      kcalObjetivo: 2500,
      kcalConsumidas: 2000,
      kcalRestantes: 500,
      carbohidratosPorcentaje: 50,
      grasasPorcentaje: 30,
      proteinasPorcentaje: 20,
      
    }
  }
}