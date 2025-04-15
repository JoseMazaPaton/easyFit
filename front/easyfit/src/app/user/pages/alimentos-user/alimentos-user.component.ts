import { Component } from '@angular/core';
import { AlimentosSwitcherComponent } from '../../../shared/components/alimentos-switcher/alimentos-switcher.component';
import { AlimentosFiltroComponent } from '../../../shared/components/alimentos-filtro/alimentos-filtro.component';
import { Alimento } from '../../../models/interfaces/alimento';
import { AlimentosService } from '../../../models/services/alimentos.service';
import { AlimentosListaComponent } from '../../../shared/components/alimentos-lista/alimentos-lista.component';
import { Categoria } from '../../../models/interfaces/categoria';
import { CategoriasService } from '../../../models/services/categorias.service';
import { AlimentosFormularioComponent } from '../../../shared/components/alimentos-formulario/alimentos-formulario.component';

@Component({
  selector: 'app-alimentos-user',
  standalone: true,
  imports: [AlimentosSwitcherComponent, AlimentosFiltroComponent, AlimentosListaComponent, AlimentosFormularioComponent],
  templateUrl: './alimentos-user.component.html',
  styleUrl: './alimentos-user.component.css'
})
export class AlimentosUserComponent {
  modoActual: 'todos' | 'favoritos' = 'todos';

  alimentosOriginales: Alimento[] = [];
  alimentosFiltrados: Alimento[] = [];

  formularioVisible: boolean = false;
  alimentoAEditar: Alimento | null = null;

  categorias: Categoria[] = [];
  
  
  constructor(private alimentosService: AlimentosService,
            private categoriaService: CategoriasService
  ) {}

  ngOnInit(): void {
    this.cargarCategorias();  
    this.cargarAlimentos(); 
  }

  

  cargarCategorias(): void {
    this.categoriaService.getCategorias().subscribe(cat => {
      this.categorias = cat;
    });
  }

  cargarAlimentos(): void {
    const fuente = this.modoActual === 'favoritos'
      ? this.alimentosService.getMisAlimentos()
      : this.alimentosService.getAlimentos();
  
    fuente.subscribe({
      next: (alimentos) => {
        this.alimentosOriginales = alimentos;
        this.alimentosFiltrados = alimentos;
      },
      error: (e) => {
        console.error('❌ Error al cargar alimentos:', e);
      }
    });
  }
  
  onModoCambio(nuevoModo: 'todos' | 'favoritos'): void {
    if (this.modoActual !== nuevoModo) {
      this.modoActual = nuevoModo;
      this.cargarAlimentos(); // carga según el modo
    }
  }

  onBuscar(texto: string): void {
    const normalizar = (str: string) =>
      str.normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase();
  
    if (!texto) {
      this.alimentosFiltrados = this.alimentosOriginales;
    } else {
      const textoNormalizado = normalizar(texto);
      this.alimentosFiltrados = this.alimentosOriginales.filter(alimento =>
        normalizar(alimento.nombre).includes(textoNormalizado)
      );
    }
  }

  abrirFormularioNuevoAlimento(): void {
    this.alimentoAEditar = null; // ← 🔑 importante
    this.formularioVisible = true;
  }

  onEditarAlimento(alimento: Alimento): void {
    this.alimentoAEditar = alimento;
    this.formularioVisible = true;
  }

  cerrarFormulario(): void {
    this.formularioVisible = false;
    this.alimentoAEditar = null;
  }

  onEliminarAlimento(alimento: Alimento): void {
    console.log('🧪 Alimento recibido para eliminar:', alimento);
  
    if (!alimento.idAlimento) {
      console.error('❌ El alimento no tiene idAlimento:', alimento);
      return;
    }
  
    if (confirm(`¿Eliminar el alimento "${alimento.nombre}"?`)) {
      this.alimentosService.eliminarAlimento(alimento.idAlimento).subscribe({
        next: () => {
          console.log('✅ Alimento eliminado correctamente');
          this.cargarAlimentos();
        },
        error: (e) => {
          console.error('❌ Error al eliminar alimento:', e);
        }
      });
    }
  }

  onAlimentoCreado(): void {
    this.cerrarFormulario();
    this.cargarAlimentos(); // refresca lista
  }

  onAlimentoActualizado(): void {
    this.formularioVisible = false;
    this.alimentoAEditar = null;
    this.cargarAlimentos();
  }

}
