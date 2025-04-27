import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Categoria } from '../../../models/interfaces/categoria';
import { CategoriaBotonesComponent } from "../categoria-botones/categoria-botones.component";

@Component({
  selector: 'app-categoria-card',
  standalone: true,
  imports: [CategoriaBotonesComponent],
  templateUrl: './categoria-card.component.html',
  styleUrl: './categoria-card.component.css'
})
export class CategoriaCardComponent {
  
  
  @Input() categorias: Categoria[] = [];

  @Output() editarCategoria = new EventEmitter<Categoria>();
  @Output() eliminarCategoria = new EventEmitter<Categoria>();

  onEditar(categoria: Categoria) {
    this.editarCategoria.emit(categoria);
  }

  onEliminar(categoria: Categoria) {
    this.eliminarCategoria.emit(categoria);
  }
}