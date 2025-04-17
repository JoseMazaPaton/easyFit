import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { BotonesComidaDiarioComponent } from "../botones-comida-diario/botones-comida-diario.component";
import { CommonModule } from '@angular/common';
import { IComidaDiariaDto } from '../../../../models/interfaces/IComidaDiario';
import { IResumenComida } from '../../../../models/interfaces/IResumenComida';
import { ComidaService } from '../../../../models/services/comida.service';

@Component({
  selector: 'app-comida-diario-card',
  standalone: true,
  imports: [CommonModule, BotonesComidaDiarioComponent],
  templateUrl: './comida-diario-card.component.html',
  styleUrl: './comida-diario-card.component.css'
})
export class ComidaDiarioCardComponent {

  @Input() comida!: IComidaDiariaDto;
  @Output() refresh = new EventEmitter<void>();
  comidaService = inject(ComidaService);  
  
  resumen!: IResumenComida

  ngOnInit(): void {
    this.comidaService.getResumenComida(this.comida.idComida).subscribe({
      next: (resumen) => {
        this.resumen = resumen;
      },
      error: (err) => {
        console.error('Error al cargar resumen de comida', err);
      }
    });
  }
  
}


