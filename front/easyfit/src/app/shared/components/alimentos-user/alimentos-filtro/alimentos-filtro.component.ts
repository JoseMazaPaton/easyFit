import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-alimentos-filtro',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './alimentos-filtro.component.html',
  styleUrl: './alimentos-filtro.component.css'
})
export class AlimentosFiltroComponent {
  textoBusqueda: string = '';

  @Output() buscar = new EventEmitter<string>();

  onBuscar() {
    this.buscar.emit(this.textoBusqueda.trim());
  }

  onInputChange() {
    this.buscar.emit(this.textoBusqueda.trim());
  }

  borrarBusqueda() {
    this.textoBusqueda = '';
    this.buscar.emit('');
  }
}
