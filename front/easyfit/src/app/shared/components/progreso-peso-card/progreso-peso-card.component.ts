import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-progreso-peso-card',
  standalone: true,
  imports: [],
  templateUrl: './progreso-peso-card.component.html',
  styleUrl: './progreso-peso-card.component.css'
})
export class ProgresoPesoCardComponent {
  @Input() pesoInicial: number = 0;
  @Input() pesoActual: number = 0;
  @Input() pesoObjetivo: number = 0;

  get diferencia(): number {
    return parseFloat((this.pesoInicial - this.pesoActual).toFixed(1));
  }

  get mensaje(): string {
    if (this.diferencia > 0) return `${this.diferencia} kg perdidos`;
    if (this.diferencia < 0) return `${Math.abs(this.diferencia)} kg ganados`;
    return `Peso estable`;
  }

  get porcentajeProgreso(): number {
    const total = Math.abs(this.pesoInicial - this.pesoObjetivo);
    const progreso = Math.abs(this.pesoInicial - this.pesoActual);
    if (total === 0) return 0;
    return Math.min(100, Math.max(0, (progreso / total) * 100));
  }

  get colorProgreso(): string {
    return this.pesoActual < this.pesoInicial ? '#0099ff' : '#f87171'; // rojo si sube
  }

  
}
