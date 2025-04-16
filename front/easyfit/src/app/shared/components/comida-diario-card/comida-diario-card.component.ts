import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BotonesComidaDiarioComponent } from "../botones-comida-diario/botones-comida-diario.component";
import { BotonesAlimentoDiarioComponent } from "../botones-alimento-diario/botones-alimento-diario.component";
import { CommonModule } from '@angular/common';
import { IComidaDiariaDto } from '../../../models/interfaces/IComidaDiario';



@Component({
  selector: 'app-comida-diario-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './comida-diario-card.component.html',
  styleUrl: './comida-diario-card.component.css'
})
export class ComidaDiarioCardComponent {

  @Input() comida!: IComidaDiariaDto;
  @Output() refresh = new EventEmitter<void>();
}


