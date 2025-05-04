import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-categoria-filtro',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './categoria-filtro.component.html',
  styleUrl: './categoria-filtro.component.css'
})
export class CategoriaFiltroComponent {
  
  textoBusqueda: string = '';

  @Output() buscar = new EventEmitter<string>();

  onInputChange() {
    this.buscar.emit(this.textoBusqueda);
  }
}