import { NgStyle } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-consumo-cal-card',
  standalone: true,
  imports: [NgStyle],
  templateUrl: './consumo-cal-card.component.html',
  styleUrl: './consumo-cal-card.component.css'
})
export class ConsumoCaloriasSimplesComponent {
  @Input() datos: { fecha: string; consumidas: number; objetivo: number }[] = [];

  maxAltura = 100;

    
  getAltura(valor: number): string {
    return `${Math.min((valor / 2500) * this.maxAltura, this.maxAltura)}px`;
  }
  
  getColor(valor: number, objetivo: number): string {
    return valor >= objetivo ? '#0099ff' : '#90cdf4';
  }
}



