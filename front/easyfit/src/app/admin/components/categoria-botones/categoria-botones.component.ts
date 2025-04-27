import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-categoria-botones',
  standalone: true,
  imports: [],
  templateUrl: './categoria-botones.component.html',
  styleUrl: './categoria-botones.component.css'
})
export class CategoriaBotonesComponent {

  @Output() editar = new EventEmitter<void>();
  @Output() eliminar = new EventEmitter<void>();

  onEditar() {
    this.editar.emit();
  }

  onEliminar() {
    this.eliminar.emit();
  }
}
