import { Component, EventEmitter, Input, Output, ChangeDetectorRef, OnChanges, SimpleChanges } from '@angular/core';
import { Alimento } from '../../../../models/interfaces/alimento';
import { Categoria } from '../../../../models/interfaces/categoria';
import { CommonModule } from '@angular/common';
import { CategoriasService } from '../../../../models/services/categorias.service';

@Component({
  selector: 'app-alimentos-lista',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './alimentos-lista.component.html',
  styleUrl: './alimentos-lista.component.css'
})
export class AlimentosListaComponent {
  @Input() alimentos: Alimento[] = [];
  @Input() modo: 'todos' | 'favoritos' | 'seleccionar' = 'todos';

  @Output() editarAlimento = new EventEmitter<Alimento>();
  @Output() eliminarAlimento = new EventEmitter<Alimento>();
  @Output() seleccionarAlimento = new EventEmitter<Alimento>();

  categorias: Categoria[] = [];
  categoriaMap: Record<number, string> = {};

  constructor(private categoriaService: CategoriasService) {}

  ngOnInit(): void {
    this.categoriaService.categorias$.subscribe((categorias: Categoria[]) => {
      this.categorias = categorias;
      this.generarCategoriaMap(categorias);
    });
  }

  generarCategoriaMap(categorias: Categoria[]): void {
    this.categoriaMap = {};
    for (const cat of categorias) {
      this.categoriaMap[cat.idCategoria] = cat.nombre;
    }
  }

  onEditar(alimento: Alimento): void {
    this.editarAlimento.emit(alimento);
  }

  onEliminar(alimento: Alimento): void {
    this.eliminarAlimento.emit(alimento);
  }

  onSeleccionar(alimento: Alimento): void {
    this.seleccionarAlimento.emit(alimento);
  }

  getNombreCategoria(idCategoria: number): string {
    return this.categoriaMap[idCategoria] || 'Sin categoría';
  }
}