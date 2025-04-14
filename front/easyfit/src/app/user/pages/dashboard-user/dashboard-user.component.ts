import { Component } from '@angular/core';
import { DashboardService } from '../../../models/services/dashboard.service';
import { ObjetivoService } from '../../../models/services/objetivo.service';
import { ObjetivoResponse } from '../../../models/interfaces/objetivo-response';
import { ResumenDiario } from '../../../models/interfaces/resumen-diario';
import { CaloriasCardComponent } from "../../../shared/components/calorias-card/calorias-card.component";
import { ProgresoPesoCardComponent } from "../../../shared/components/progreso-peso-card/progreso-peso-card.component";
import { HistorialPeso } from '../../../models/interfaces/historial-peso';
import { ConsumoCaloriasSimplesComponent } from "../../../shared/components/consumo-cal-card/consumo-cal-card.component";

@Component({
  selector: 'app-dashboard-user',
  standalone: true,
  imports: [CaloriasCardComponent, ProgresoPesoCardComponent, ConsumoCaloriasSimplesComponent],
  templateUrl: './dashboard-user.component.html',
  styleUrl: './dashboard-user.component.css'
})
export class DashboardUserComponent {

  usuario = JSON.parse(localStorage.getItem('usuario') || '{}');

  kcalObjetivo: number = 0;
  kcalConsumidas: number = 0;
  kcalRestantes: number = 0;
  porcentaje: number = 0;

  pesoInicial: number = 0;
  pesoActual: number = 0;
  pesoObjetivo: number = 0;

  consumoSemanal: { fecha: string; consumidas: number; objetivo: number }[] = [];

  constructor (private dashboardService: DashboardService,
              private objetivoService: ObjetivoService) { }

  ngOnInit(): void {
    this.dashboardService.getResumenDiario().subscribe({
      next: (resumen: ResumenDiario) => {
        this.kcalObjetivo = resumen.kcalObjetivo;
        this.kcalConsumidas = resumen.kcalConsumidas;
        this.kcalRestantes = resumen.kcalRestantes;
            
        this.calcularPorcentaje();
      },
      error: (err) => {
        console.warn('No hay consumo registrado hoy, se usará el objetivo como referencia:', err);
            
        // En caso de error (404, por ejemplo), usamos los datos del objetivo
        this.objetivoService.getObjetivosUsuario().subscribe({
          next: (objetivo: ObjetivoResponse) => {
            this.kcalObjetivo = objetivo.valores.kcalObjetivo;
            this.kcalConsumidas = 0;
            this.kcalRestantes = this.kcalObjetivo;
            this.porcentaje = 0;
          },
          error: (e) => console.error('Error al obtener objetivo nutricional:', e)
        });
      }
    });
    this.dashboardService.getResumenPesos().subscribe({
      next: (resumen: HistorialPeso) => {
        this.pesoInicial = resumen.pesoInicial;
        this.pesoActual = resumen.pesoActual;
        this.pesoObjetivo = resumen.pesoObjetivo; // <-- esto
      },
      error: (err) => {
        console.error('Error al obtener resumen de peso:', err);
      }
    });

    this.dashboardService.getResumenCalorias7Dias().subscribe(resumen => {
      this.consumoSemanal = resumen.map(d => ({
        fecha: this.formatearFecha(d.fecha),
        consumidas: d.kcalConsumidas,
        objetivo: d.kcalObjetivo
      }));
    });
  }
  
            
  private calcularPorcentaje(): void {
    const p = (this.kcalConsumidas / this.kcalObjetivo) * 100;
    this.porcentaje = isNaN(p) ? 0 : Math.min(Math.max(p, 0), 100);
  }

  formatearFecha(fechaISO: string): string {
    const f = new Date(fechaISO);
    return f.toLocaleDateString('es-ES', {
      day: '2-digit',
      month: '2-digit'
    }); // Ejemplo: "14/04"
  }
}
