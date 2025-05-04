import { Component } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { AdminService } from '../../../../models/services/admin.service';

Chart.register(...registerables);


@Component({
  selector: 'app-card-usuarios-genero',
  standalone: true,
  imports: [],
  templateUrl: './card-usuarios-genero.component.html',
  styleUrl: './card-usuarios-genero.component.css'
})
export class CardUsuariosGeneroComponent {

  totalHombres: number = 0;
  totalMujeres: number = 0;

  constructor(private adminService: AdminService) {
    Chart.register(...registerables); 
  }

  ngAfterViewInit(): void {
    this.adminService.resumen$.subscribe(resumen => {
      if (resumen) {
        this.totalHombres = resumen.totalHombres;
        this.totalMujeres = resumen.totalMujeres;
        this.dibujarGrafico();
      }
    });
  }

  dibujarGrafico(): void {
    const canvas = document.getElementById('generoChart') as HTMLCanvasElement;
    const ctx = canvas.getContext('2d');

    if (!ctx) return;

    new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: ['Hombres', 'Mujeres'],
        datasets: [{
          data: [this.totalHombres, this.totalMujeres],
          backgroundColor: ['#0099ff', '#5ce1e6'], // Colores bonitos :)
          borderWidth: 1
        }]
      },
      options: {
        responsive: false,
        plugins: {
          legend: { display: false }
        },
        cutout: '70%'
      }
    });
  }
}