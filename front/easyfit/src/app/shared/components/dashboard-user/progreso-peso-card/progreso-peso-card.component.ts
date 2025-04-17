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

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.resumenPeso$.subscribe(data => {
      if (data) {
        this.pesoInicial = data.pesoInicial;
        this.pesoActual = data.pesoActual;
        this.pesoObjetivo = data.pesoObjetivo;

        const total = this.pesoInicial - this.pesoObjetivo;
        const progreso = this.pesoInicial - this.pesoActual;

        this.porcentajeProgreso = total > 0 ? (progreso / total) * 100 : 0;
      }
    });
  }

  getPesoPerdido(): number {
    return Math.max(this.pesoInicial - this.pesoActual, 0);
  }
}