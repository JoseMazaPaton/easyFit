import { Component } from '@angular/core';
import { DashboardService } from '../../../../models/services/dashboard.service';

@Component({
  selector: 'app-progreso-peso-card',
  standalone: true,
  templateUrl: './progreso-peso-card.component.html',
  styleUrl: './progreso-peso-card.component.css',
  imports: []
})
export class ProgresoPesoCardComponent {
  pesoInicial: number = 0;
  pesoActual: number = 0;
  pesoObjetivo: number = 0;
  porcentajeProgreso: number = 0;

  objetivoUsuario: string = 'PERDERPESO'; // valor por defecto

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.resumenPeso$.subscribe(data => {
      if (data) {
        this.pesoInicial = data.pesoInicial;
        this.pesoActual = data.pesoActual;
        this.pesoObjetivo = data.pesoObjetivo;
    
        this.objetivoUsuario = data.objetivoUsuario || 'PERDERPESO';
    
        const total = this.calcularTotalCambio();
        const progreso = this.calcularProgresoActual();
    
        this.porcentajeProgreso = total > 0 ? (progreso / total) * 100 : 0;
      }
    });
    
  }

  calcularTotalCambio(): number {
    if (this.objetivoUsuario === 'GANARPESO') {
      return this.pesoObjetivo - this.pesoInicial;
    } else if (this.objetivoUsuario === 'PERDERPESO') {
      return this.pesoInicial - this.pesoObjetivo;
    }
    return 3; // margen de tolerancia en "mantener"
  }
  
  calcularProgresoActual(): number {
    if (this.objetivoUsuario === 'GANARPESO') {
      return this.pesoActual - this.pesoInicial;
    } else if (this.objetivoUsuario === 'PERDERPESO') {
      return this.pesoInicial - this.pesoActual;
    }
    return Math.abs(this.pesoInicial - this.pesoActual);
  }
  

  getPesoPerdido(): number {
    return Math.max(this.pesoInicial - this.pesoActual, 0);
  }

  getTextoProgreso(): string {
    const diferencia = +(this.calcularProgresoActual()).toFixed(1);
  
    if (this.objetivoUsuario === 'GANARPESO') {
      return diferencia <= 0 ? 'Sin progreso' : `${diferencia} kg ganados`;
    }
  
    if (this.objetivoUsuario === 'PERDERPESO') {
      return diferencia <= 0 ? 'Sin progreso' : `${diferencia} kg perdidos`;
    }
  
    return `Cambio: ${diferencia} kg`;
  }
  
}