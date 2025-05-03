import { CommonModule} from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { DashboardService } from '../../../../models/services/dashboard.service';
import { NgChartsModule } from 'ng2-charts';

Chart.register(...registerables);

@Component({
  selector: 'app-consumo-cal-card',
  standalone: true,
  imports: [CommonModule, NgChartsModule],
  templateUrl: './consumo-cal-card.component.html',
  styleUrl: './consumo-cal-card.component.css'
})
export class ConsumoCaloriasSimplesComponent {
  @ViewChild('consumoChart', { static: true }) chartRef!: ElementRef<HTMLCanvasElement>;
  chart!: Chart;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.calorias7dias$.subscribe((datos) => {
      if (datos) {
        const labels = datos.map(d => d.fecha);
        const objetivo = datos.map(d => d.kcalObjetivo);
        const consumidas = datos.map(d => d.kcalConsumidas);
        this.renderChart(labels, objetivo, consumidas);
      }
    });
  }

  renderChart(labels: string[], objetivo: number[], consumidas: number[]) {
    if (this.chart) this.chart.destroy();

    this.chart = new Chart(this.chartRef.nativeElement, {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: 'Objetivo',
            data: objetivo,
            backgroundColor: '#5ce1e6',
            borderRadius: 6,
            categoryPercentage: 0.6,
            barPercentage: 1.0
          },
          {
            label: 'Consumidas',
            data: consumidas,
            backgroundColor: '#0099ff',
            borderRadius: 6,
            categoryPercentage: 0.6,
            barPercentage: 1.0
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'top',
            labels: {
              color: '#444',
              font: {
                size: 13,
                weight: '500'
              },
              usePointStyle: true,
              pointStyle: 'circle',
              padding: 20
            }
          }
        },
        scales: {
          x: {
            grid: { display: false },
            ticks: {
              color: '#666',
              font: {
                size: 12,
                weight: 'bold'
              }
            }
          },
          y: {
            beginAtZero: true,
            ticks: {
              color: '#666',
              font: {
                size: 12
              }
            },
            grid: {
              color: '#e0e0e0'
            }
          }
        }
      }
    });
  }
}