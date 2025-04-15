import { Component, EventEmitter, Input, Output, ChangeDetectorRef, OnChanges, SimpleChanges } from '@angular/core';
import { Alimento } from '../../../models/interfaces/alimento';
import { Categoria } from '../../../models/interfaces/categoria';
import { CommonModule } from '@angular/common';
import { CategoriasService } from '../../../models/services/categorias.service';

@Component({
  selector: 'app-alimentos-lista',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './alimentos-lista.component.html',
  styleUrl: './alimentos-lista.component.css'
})
export class AlimentosListaComponent {
  @Input() alimentos: Alimento[] = [];
  @Input() modo: 'todos' | 'favoritos' = 'todos';

  @Output() editarAlimento = new EventEmitter<Alimento>();
  @Output() eliminarAlimento = new EventEmitter<Alimento>();

  categorias: Categoria[] = [];
  categoriaMap: Record<number, string> = {};

  constructor(private categoriaService: CategoriasService) {}


  ngOnInit(): void {
    this.categoriaService.getCategorias().subscribe({
      next: (categorias) => {
        this.categorias = categorias;
        this.categoriaMap = {};

        for (const cat of categorias) {
          this.categoriaMap[cat.idCategoria] = cat.nombre;
        }

        console.log('✅ Categorías cargadas en el hijo:', this.categoriaMap);
      },
      error: (e) => console.error('❌ Error al cargar categorías en hijo:', e)
    });
  }
  

  onEditar(alimento: Alimento): void {
    this.editarAlimento.emit(alimento);
  }

  onEliminar(alimento: Alimento): void {
    this.eliminarAlimento.emit(alimento); // ✅ solo emite, sin confirm
  }

  getNombreCategoria(idCategoria: number): string {
    return this.categoriaMap[idCategoria] || 'Sin categoría';
  }
}
