import { Component, ElementRef, ViewChild } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { DashboardService } from '../../../../models/services/dashboard.service';
Chart.register(...registerables);

@Component({
  selector: 'app-calorias-card',
  standalone: true,
  imports: [],
  templateUrl: './calorias-card.component.html',
  styleUrl: './calorias-card.component.css'
})
export class CaloriasCardComponent {
  @ViewChild('donutChart', { static: true }) donutChartRef!: ElementRef<HTMLCanvasElement>;
  chart!: Chart<'doughnut', number[], string>;
  resumen: any;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.resumenDiario$.subscribe(resumen => {
      if (resumen) {
        this.resumen = resumen;
        console.log(resumen)
        this.renderChart(resumen.kcalConsumidas, resumen.kcalObjetivo);
      }
    });
  }

  renderChart(consumidas: number, objetivo: number): void {
    const restantes = Math.max(objetivo - consumidas, 0);

    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart(this.donutChartRef.nativeElement.getContext('2d')!, {
      type: 'doughnut',
      data: {
        labels: ['Consumidas', 'Restantes'],
        datasets: [{
          data: [consumidas, restantes],
          backgroundColor: ['#0099ff', '#5ce1e6'],
          borderWidth: 4
        }]
      },
      options: {
        cutout: '70%',
        plugins: {
          legend: { display: false },
          tooltip: { enabled: false }
        }
      }
    });
  }
}