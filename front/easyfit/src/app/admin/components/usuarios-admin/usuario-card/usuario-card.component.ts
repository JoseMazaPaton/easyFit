import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioBotonesComponent } from "../usuario-botones/usuario-botones.component";
import { IUsuario } from '../../../../models/interfaces/IUsuario';
import { AdminService } from '../../../../models/services/admin.service';

@Component({
  selector: 'app-usuario-card',
  standalone: true,
  imports: [CommonModule, UsuarioBotonesComponent],
  templateUrl: './usuario-card.component.html',
  styleUrl: './usuario-card.component.css'
})
export class UsuarioCardComponent implements OnInit, OnChanges {  // <-- AÑADIDO OnChanges

  @Input() estadoSeleccionado: 'ACTIVO' | 'SUSPENDIDO' = 'ACTIVO';
  @Input() textoBusqueda: string = '';   // <- ¡Ahora también recibe el texto de búsqueda!

  usuarios: IUsuario[] = [];
  usuariosFiltrados: IUsuario[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.usuarios$.subscribe(usuarios => {
      this.usuarios = usuarios || [];
      this.aplicarFiltros();
    });

    this.adminService.obtenerTodosUsuarios();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['estadoSeleccionado'] || changes['textoBusqueda']) {
      this.aplicarFiltros();
    }
  }

  aplicarFiltros(): void {
    if (!this.usuarios.length) {
      this.usuariosFiltrados = [];
      return;
    }

    let filtrados = [...this.usuarios];

    // Filtrar por estado
    if (this.estadoSeleccionado === 'ACTIVO') {
      filtrados = filtrados.filter(usuario => !usuario.suspendida);
    } else if (this.estadoSeleccionado === 'SUSPENDIDO') {
      filtrados = filtrados.filter(usuario => usuario.suspendida);
    }

    // Filtrar por búsqueda de texto
    if (this.textoBusqueda.trim()) {
      const texto = this.textoBusqueda.trim().toLowerCase();
      filtrados = filtrados.filter(usuario =>
        usuario.email.toLowerCase().includes(texto) ||
        usuario.nombre.toLowerCase().includes(texto)
      );
    }

    this.usuariosFiltrados = filtrados;
  }

  refrescarListaUsuarios(): void {
    this.adminService.obtenerTodosUsuarios();
  }
}