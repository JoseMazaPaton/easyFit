import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { IComidaDiariaDto } from '../../../../models/interfaces/IComidaDiario';
import Swal from 'sweetalert2';
import { ComidaService } from '../../../../models/services/comida.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-botones-comida-diario',
  standalone: true,
  imports: [],
  templateUrl: './botones-comida-diario.component.html',
  styleUrl: './botones-comida-diario.component.css'
})
export class BotonesComidaDiarioComponent {

  @Input() comida!: IComidaDiariaDto;
  @Output() refresh = new EventEmitter<void>();
  comidaService = inject(ComidaService);

  constructor(private router: Router) {}

  // Eliminar una comida con confirmación
  onEliminarComida(): void {
    Swal.fire({
      title: `¿Eliminar "${this.comida.nombre}"?`,
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        this.comidaService.eliminarComida(this.comida.idComida).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Comida eliminada',
              showConfirmButton: false,
              timer: 1200
            });
            this.refresh.emit(); // Avisamos al padre que se refresque la vista
          },
          error: () => {
            Swal.fire('Error', 'No se pudo eliminar la comida', 'error');
          }
        });
      }
    });
  }

  // Navega al componente para añadir alimento a esta comida
  agregarAlimentoComida(): void {
    const idComida = this.comida.idComida;

    // Usamos la fecha de la comida para que al volver cargue el día correcto
    const fecha = this.comida.fecha 
      ? new Date(this.comida.fecha).toISOString().split('T')[0]
      : '';

    this.router.navigate(['/usuario/diario/alimento', idComida], {
      queryParams: { fecha }
    });
  }
}