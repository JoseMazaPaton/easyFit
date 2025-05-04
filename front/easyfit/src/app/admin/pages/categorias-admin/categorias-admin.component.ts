import { Component } from '@angular/core';
import { CategoriaFiltroComponent } from "../../components/categoria-admin/categoria-filtro/categoria-filtro.component";
import { Categoria } from '../../../models/interfaces/categoria';
import { CategoriasService } from '../../../models/services/categorias.service';
import Swal from 'sweetalert2';
import { CategoriaCardComponent } from '../../components/categoria-admin/categoria-card/categoria-card.component';
import { CategoriaFormComponent } from '../../components/categoria-admin/categoria-form/categoria-form.component';

@Component({
  selector: 'app-categorias-admin',
  standalone: true,
  imports: [CategoriaFiltroComponent, CategoriaCardComponent, CategoriaFormComponent],
  templateUrl: './categorias-admin.component.html',
  styleUrl: './categorias-admin.component.css'
})
export class CategoriasAdminComponent {

  categorias: Categoria[] = [];
  categoriasFiltradas: Categoria[] = [];

  formularioVisible = false;
  categoriaAEditar: Categoria | null = null;

  constructor(private categoriasService: CategoriasService) {}

  ngOnInit(): void {
    this.categoriasService.categorias$.subscribe(categorias => {
      this.categorias = categorias;
      this.categoriasFiltradas = categorias;
    });

    this.categoriasService.cargarCategorias();
  }

  onBuscar(texto: string) {
    const filtro = texto.toLowerCase();
    this.categoriasFiltradas = this.categorias.filter(categoria =>
      categoria.nombre.toLowerCase().includes(filtro)
    );
  }

  abrirFormularioNuevaCategoria() {
    this.categoriaAEditar = null;
    this.formularioVisible = true;
  }

  onEditarCategoria(categoria: Categoria) {
    this.categoriaAEditar = { ...categoria };
    this.formularioVisible = true;
  }

  onEliminarCategoria(categoria: Categoria) {
    Swal.fire({
      title: '¿Estás seguro?',
      text: `Esta acción eliminará la categoría "${categoria.nombre}" y todos los alimentos de la categoria.`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.categoriasService.eliminarCategoria(categoria.idCategoria).subscribe(() => {
          this.categoriasService.cargarCategorias();
          Swal.fire(
            '¡Eliminado!',
            'La categoría ha sido eliminada correctamente.',
            'success'
          );
        }, (error) => {
          Swal.fire(
            'Error',
            'Hubo un problema al eliminar la categoría.',
            'error'
          );
        });
      }
    });
  }

  cerrarFormulario() {
    this.formularioVisible = false;
  }

  onCategoriaCreada(categoria: Categoria) {
    this.categoriasService.crearCategoria(categoria).subscribe(() => {
      this.categoriasService.cargarCategorias();
      this.formularioVisible = false;
    });
  }

  onCategoriaActualizada(categoria: Categoria) {
    if (categoria.idCategoria) {
      this.categoriasService.modificarCategoria(categoria.idCategoria, categoria).subscribe(() => {
        this.categoriasService.cargarCategorias();
        this.formularioVisible = false;
      });
    }
  }
}