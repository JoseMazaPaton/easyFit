import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-calorias-card',
  standalone: true,
  imports: [],
  templateUrl: './calorias-card.component.html',
  styleUrl: './calorias-card.component.css'
})
export class CaloriasCardComponent {
  @Input() kcalObjetivo: number = 0;
  @Input() kcalConsumidas: number = 0;
  @Input() kcalRestantes: number = 0;
  @Input() porcentaje: number = 0;
}
