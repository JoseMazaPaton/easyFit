import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-usuario-filtro',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './usuario-filtro.component.html',
  styleUrl: './usuario-filtro.component.css'
})
export class UsuarioFiltroComponent {
  
  textoBusqueda: string = '';

  @Output() busqueda = new EventEmitter<string>();

  onInputChange(): void {
    this.busqueda.emit(this.textoBusqueda.trim().toLowerCase());
  }
}