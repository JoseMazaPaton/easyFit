import { Component } from '@angular/core';
import { ComidaService } from '../../../models/services/comida.service';
import { ResumenComidasDiarioComponent } from "../../../shared/components/diario-user/resumen-comidas-diario/resumen-comidas-diario.component";
import { CommonModule, formatDate } from '@angular/common';
import { IComidaDiariaDto } from '../../../models/interfaces/IComidaDiario';
import Swal from 'sweetalert2';
import { ComidaDiarioCardComponent } from "../../../shared/components/diario-user/comida-diario-card/comida-diario-card.component";
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-diario-user',
  standalone: true,
  imports: [CommonModule, ResumenComidasDiarioComponent, ComidaDiarioCardComponent],
  templateUrl: './diario-user.component.html',
  styleUrl: './diario-user.component.css'
})
export class DiarioUserComponent {
  arrayComidas: IComidaDiariaDto[] = [];
  fechaSeleccionada: Date = new Date();

  constructor(
    private comidaService: ComidaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Cuando se abre el componente, recogemos la fecha de los parámetros si existe
    this.route.queryParams.subscribe(params => {
      const fechaParam = params['fecha'];
      this.fechaSeleccionada = fechaParam ? new Date(fechaParam) : new Date();

      this.obtenerComidas();
    });
  }

  // Pedimos al backend las comidas del día seleccionado
  obtenerComidas(): void {
    const fechaString = formatDate(this.fechaSeleccionada, 'yyyy-MM-dd', 'en-US');

    this.comidaService.getComidasDelDia(fechaString).subscribe({
      next: (data) => {
        this.arrayComidas = data;
      },
      error: () => {
        console.error('Error al obtener comidas');
      }
    });
  }

  // Cambia el día y actualiza la URL con la nueva fecha
  cambiarFecha(dias: number): void {
    const nuevaFecha = new Date(this.fechaSeleccionada);
    nuevaFecha.setDate(nuevaFecha.getDate() + dias);

    const fechaFormateada = formatDate(nuevaFecha, 'yyyy-MM-dd', 'en-US');

    this.router.navigate([], {
      queryParams: { fecha: fechaFormateada },
      queryParamsHandling: 'merge'
    });
  }

  // Abre un diálogo para que el usuario escriba el nombre de una nueva comida
  abrirDialogoCrearComida(): void {
    Swal.fire({
      title: 'Nueva comida',
      input: 'text',
      inputLabel: 'Nombre de la comida',
      inputPlaceholder: 'Introduce el nombre',
      showCancelButton: true,
      confirmButtonText: 'Crear'
    }).then(result => {
      const nombre = result.value?.trim();

      if (result.isConfirmed && nombre) {
        const nuevaComida = {
          nombre,
          orden: this.arrayComidas.length + 1,
          fecha: this.fechaSeleccionada
        };

        this.comidaService.crearComida(nuevaComida).subscribe({
          next: () => this.obtenerComidas(),
          error: () => {
            Swal.fire('Error', 'No se pudo crear la comida', 'error');
          }
        });
      }
    });
  }
}