import { Component } from '@angular/core';
import { Alimento } from '../../../models/interfaces/alimento';
import { Categoria } from '../../../models/interfaces/categoria';
import { AlimentosService } from '../../../models/services/alimentos.service';
import { CategoriasService } from '../../../models/services/categorias.service';
import { AlimentosListaComponent } from "../../../user/components/alimentos-user/alimentos-lista/alimentos-lista.component";
import { AlimentosFormularioComponent } from "../../../user/components/alimentos-user/alimentos-formulario/alimentos-formulario.component";
import { AlimentosFiltroComponent } from '../../../user/components/alimentos-user/alimentos-filtro/alimentos-filtro.component';

@Component({
  selector: 'app-alimentos-admin',
  standalone: true,
  imports: [AlimentosListaComponent, AlimentosFormularioComponent, AlimentosFiltroComponent],
  templateUrl: './alimentos-admin.component.html',
  styleUrl: './alimentos-admin.component.css'
})
export class AlimentosAdminComponent {

  alimentosOriginales: Alimento[] = [];
  alimentosFiltrados: Alimento[] = [];

  formularioVisible: boolean = false;
  alimentoAEditar: Alimento | null = null;

  categorias: Categoria[] = [];

  constructor(
    private alimentosService: AlimentosService,
    private categoriaService: CategoriasService
  ) {}

  ngOnInit(): void {
    this.cargarCategorias();
    this.cargarAlimentos();
  }

  cargarCategorias(): void {
    this.categoriaService.categorias$.subscribe({
      next: (cat: Categoria[]) => this.categorias = cat,
      error: (e: any) => console.error('❌ Error al cargar categorías:', e)
    });
  }

  cargarAlimentos(): void {
    this.alimentosService.getAlimentos().subscribe({
      next: (alimentos: Alimento[]) => {
        this.alimentosOriginales = alimentos;
        this.alimentosFiltrados = alimentos;
      },
      error: (e: any) => {
        console.error('❌ Error al cargar alimentos:', e);
      }
    });
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
    this.alimentoAEditar = null;
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
        error: (e: any) => {
          console.error('❌ Error al eliminar alimento:', e);
        }
      });
    }
  }

  onAlimentoCreado(): void {
    this.cerrarFormulario();
    this.cargarAlimentos();
  }

  onAlimentoActualizado(): void {
    this.cerrarFormulario();
    this.cargarAlimentos();
  }

}