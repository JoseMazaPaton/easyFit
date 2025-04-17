import { Component } from '@angular/core';
import { ComidaService } from '../../../models/services/comida.service';
import { ResumenComidasDiarioComponent } from "../../../shared/components/diario-user/resumen-comidas-diario/resumen-comidas-diario.component";
import { CommonModule, formatDate } from '@angular/common';
import { IComidaDiariaDto } from '../../../models/interfaces/IComidaDiario';
import Swal from 'sweetalert2';
import { ComidaDiarioCardComponent } from "../../../shared/components/diario-user/comida-diario-card/comida-diario-card.component";

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

  constructor(private comidaService: ComidaService) {}

  ngOnInit(): void {
    this.obtenerComidas();
  }

  obtenerComidas(): void {
    const fechaString = formatDate(this.fechaSeleccionada, 'yyyy-MM-dd', 'en-US');
    this.comidaService.getComidasDelDia(fechaString).subscribe({
      next: (data) => {
        this.arrayComidas = data;
      },
      error: (err) => {
        console.error('Error al obtener comidas', err);
      }
    });
  }

  cambiarFecha(dias: number): void {
    this.fechaSeleccionada = new Date(this.fechaSeleccionada);
    this.fechaSeleccionada.setDate(this.fechaSeleccionada.getDate() + dias);
    this.obtenerComidas();
  }

  abrirDialogoCrearComida() {
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
          orden: this.arrayComidas.length + 1, // orden dinámico según cuántas haya
          fecha: this.fechaSeleccionada // también puedes omitirlo y lo pone el backend
        };
  
        this.comidaService.crearComida(nuevaComida).subscribe({
          next: () => this.obtenerComidas(),
          error: err => {
            console.error(err);
            Swal.fire('Error', 'No se pudo crear la comida', 'error');
          }
        });
      }
    });
  }
}