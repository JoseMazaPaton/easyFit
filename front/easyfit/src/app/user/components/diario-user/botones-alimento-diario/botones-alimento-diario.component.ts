import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { IComidaDiariaDto } from '../../../../models/interfaces/IComidaDiario';
import { ComidaService } from '../../../../models/services/comida.service';
import { Alimento } from '../../../../models/interfaces/alimento';

@Component({
  selector: 'app-botones-alimento-diario',
  standalone: true,
  imports: [],
  templateUrl: './botones-alimento-diario.component.html',
  styleUrl: './botones-alimento-diario.component.css'
})
export class BotonesAlimentoDiarioComponent {
  

  @Input() alimento!: Alimento;
  @Input() idComida!: number;
  @Output() refresh = new EventEmitter<void>();

  comidaService = inject(ComidaService);

  constructor(private router: Router) {}

  // 🗑️ Eliminar alimento de la comida
  onEliminarAlimento(): void {
    Swal.fire({
      title: `¿Eliminar "${this.alimento.nombre}"?`,
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then(result => {
      if (result.isConfirmed) {
        this.comidaService.eliminarAlimentoDeComida(this.idComida, this.alimento.idAlimento).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Alimento eliminado',
              showConfirmButton: false,
              timer: 1200
            });
            this.refresh.emit(); // 🚀 Notifica al padre que recargue los datos
          },
          error: () => {
            Swal.fire('Error', 'No se pudo eliminar el alimento', 'error');
          }
        });
      }
    });
  }

  // ✏️ Editar cantidad del alimento
  onEditarCantidad(): void {
    Swal.fire({
      title: `Editar cantidad de "${this.alimento.nombre}"`,
      input: 'number',
      inputLabel: 'porciones cada 100 gramos',
      inputValue: this.alimento.cantidad,
      inputAttributes: {
        min: '0.1',
        step: '0.1'
      },
      showCancelButton: true,
      confirmButtonText: 'Actualizar',
      cancelButtonText: 'Cancelar'
    }).then(result => {
      if (result.isConfirmed) {
        const nuevaCantidad = parseFloat(result.value);

        if (isNaN(nuevaCantidad) || nuevaCantidad <= 0) {
          Swal.fire('Cantidad inválida', 'Introduce un valor válido mayor a 0', 'error');
          return;
        }

        this.comidaService.actualizarCantidadAlimento(
          this.idComida,
          this.alimento.idAlimento,
          nuevaCantidad
        ).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Cantidad actualizada',
              showConfirmButton: false,
              timer: 1000
            });
            this.refresh.emit(); // ✅ Volver a cargar
          },
          error: () => {
            Swal.fire('Error', 'No se pudo actualizar la cantidad', 'error');
          }
        });
      }
    });
  }
}