import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { IComidaDiariaDto } from '../../../../models/interfaces/IComidaDiario';
import Swal from 'sweetalert2';
import { ComidaService } from '../../../../models/services/comida.service';


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

  // onAñadirAlimentos() {
  //   // Aquí puedes abrir un modal o navegar
  //   console.log(`Añadir alimentos a: ${this.comida.nombre}`);
  //   // Por ejemplo: this.router.navigate(['/alimentos'], { queryParams: { idComida: this.comida.idComida } })
  // }
  onEliminarComida() {
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

            this.refresh.emit(); // Notifica al padre que refresque
          },
          error: (err) => {
            Swal.fire('Error', 'No se pudo eliminar la comida', 'error');
            console.error(err);
          }
        });
      }
    });
  }
  
}