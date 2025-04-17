import { Component, ElementRef, ViewChild } from '@angular/core';
import { AdminService } from '../../../models/services/admin.service';
import { CommonModule } from '@angular/common';
import { NgChartsModule } from 'ng2-charts';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-card-usuarios-meses',
  standalone: true,
  imports: [CommonModule, NgChartsModule],
  templateUrl: './card-usuarios-meses.component.html',
  styleUrl: './card-usuarios-meses.component.css'
})
export class CardUsuariosMesesComponent {
  @ViewChild('usuariosMesesChart', { static: true }) chartRef!: ElementRef<HTMLCanvasElement>;
  chart!: Chart;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.resumen$.subscribe((resumen) => {
      if (resumen && resumen.registrosPorMes) {
        const labels = resumen.registrosPorMes.map(r => r.mes);
        const hombres = resumen.registrosPorMes.map(r => r.hombres);
        const mujeres = resumen.registrosPorMes.map(r => r.mujeres);
        this.renderChart(labels, hombres, mujeres);
      }
    });
  }

  renderChart(labels: string[], hombres: number[], mujeres: number[]) {
    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart(this.chartRef.nativeElement, {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: 'Hombres',
            data: hombres,
            backgroundColor: '#5ce1e6',
            borderRadius: 6,
            categoryPercentage: 0.6,  // ancho relativo del grupo
            barPercentage: 0.8        // ancho relativo de cada barra
          },
          {
            label: 'Mujeres',
            data: mujeres,
            backgroundColor: '#0099ff',
            borderRadius: 6,
            categoryPercentage: 0.6,
            barPercentage: 0.8
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
              stepSize: 5,
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