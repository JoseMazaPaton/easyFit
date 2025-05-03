import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { BotonesComidaDiarioComponent } from "../botones-comida-diario/botones-comida-diario.component";
import { CommonModule } from '@angular/common';
import { IComidaDiariaDto } from '../../../../models/interfaces/IComidaDiario';
import { IResumenComida } from '../../../../models/interfaces/IResumenComida';
import { ComidaService } from '../../../../models/services/comida.service';
import { BotonesAlimentoDiarioComponent } from "../botones-alimento-diario/botones-alimento-diario.component";

@Component({
  selector: 'app-comida-diario-card',
  standalone: true,
  imports: [CommonModule, BotonesComidaDiarioComponent, BotonesAlimentoDiarioComponent],
  templateUrl: './comida-diario-card.component.html',
  styleUrl: './comida-diario-card.component.css'
})
export class ComidaDiarioCardComponent {

  @Input() comida!: IComidaDiariaDto;
  @Output() refresh = new EventEmitter<void>();
  comidaService = inject(ComidaService);  
  
  resumen!: IResumenComida

  ngOnInit(): void {
    // Siempre calculamos el total manualmente para garantizar precisión
    this.calcularTotalManualmente();
    
    // Aún intentamos cargar del backend, pero priorizamos nuestro cálculo
    this.cargarResumenComida();
  }
  
  cargarResumenComida(): void {
    this.comidaService.getResumenComida(this.comida.idComida).subscribe({
      next: (resumen) => {
        this.resumen = resumen;
      },
      error: (err) => {
        console.error('Error al cargar resumen de comida', err);
        // En caso de error, calculamos el total manualmente
        this.calcularTotalManualmente();
      }
    });
  }

  // Método para calcular directamente el total de calorías de la comida
  calcularTotalKcal(): number {
    let totalKcal = 0;
    
    if (this.comida && this.comida.alimentos && this.comida.alimentos.length > 0) {
      this.comida.alimentos.forEach(alimento => {
        totalKcal += alimento.kcal || 0;
      });
    }
    
    return totalKcal;
  }

  calcularTotalManualmente(): void {
    // Si no se puede obtener el resumen del backend, calculamos manualmente la suma de kcal
    let totalKcal = this.calcularTotalKcal();
    let totalProteinas = 0;
    let totalCarbohidratos = 0;
    let totalGrasas = 0;

    if (this.comida && this.comida.alimentos && this.comida.alimentos.length > 0) {
      this.comida.alimentos.forEach(alimento => {
        totalProteinas += alimento.proteinas || 0;
        totalCarbohidratos += alimento.carbohidratos || 0;
        totalGrasas += alimento.grasas || 0;
      });
    }

    // Creamos un objeto resumen manual en caso de que falle la llamada al backend
    this.resumen = {
      totalKcal: totalKcal,
      totalProteinas: totalProteinas,
      totalCarbohidratos: totalCarbohidratos,
      totalGrasas: totalGrasas
    };
  }
  
  actualizarDatosComida() {
    this.refresh.emit(); // esto se lanza desde el hijo
    // Actualizamos también el resumen de la comida
    this.calcularTotalManualmente();
  }

}

