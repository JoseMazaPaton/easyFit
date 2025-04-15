import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-alimentos-switcher',
  standalone: true,
  imports: [],
  templateUrl: './alimentos-switcher.component.html',
  styleUrl: './alimentos-switcher.component.css'
})
export class AlimentosSwitcherComponent {
  @Input() modo: 'todos' | 'favoritos' = 'todos';
  @Output() modoCambiado = new EventEmitter<'todos' | 'favoritos'>();

  cambiarModo(modo: 'todos' | 'favoritos') {
    if (modo !== this.modo) {
      this.modo = modo;
      this.modoCambiado.emit(modo);
    }
  }
}
