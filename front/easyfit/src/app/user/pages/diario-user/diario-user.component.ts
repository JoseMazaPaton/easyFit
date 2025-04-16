import { Component } from '@angular/core';
import { ComidaDiarioCardComponent } from "../../../shared/components/comida-diario-card/comida-diario-card.component";
import { ComidaService } from '../../../models/services/comida.service';
import { ResumenComidasDiarioComponent } from "../../../shared/components/resumen-comidas-diario/resumen-comidas-diario.component";
import { CommonModule, formatDate } from '@angular/common';
import { IComidaDiariaDto } from '../../../models/interfaces/IComidaDiario';

@Component({
  selector: 'app-diario-user',
  standalone: true,
  imports: [CommonModule, ResumenComidasDiarioComponent, ComidaDiarioCardComponent],
  templateUrl: './diario-user.component.html',
  styleUrl: './diario-user.component.css'
})
export class DiarioUserComponent {

  comidas: IComidaDiariaDto[] = [];
  fechaSeleccionada: Date = new Date();

  constructor(private comidaService: ComidaService) {}

  ngOnInit(): void {
    this.obtenerComidas();
  }

  obtenerComidas(): void {
    const fechaString = formatDate(this.fechaSeleccionada, 'yyyy-MM-dd', 'en-US');
    this.comidaService.getComidasDelDia(fechaString).subscribe({
      next: (data) => {
        console.log(JSON.stringify(this.comidas, null, 2));
        
        this.comidas = data;
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
}