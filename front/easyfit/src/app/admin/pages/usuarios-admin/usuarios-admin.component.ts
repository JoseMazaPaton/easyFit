import { Component, inject, ViewChild } from '@angular/core';
import { UsuarioFiltroComponent } from "../../components/usuario-filtro/usuario-filtro.component";
import { UsuarioCardComponent } from "../../components/usuario-card/usuario-card.component";
import { IUsuario } from '../../../models/interfaces/IUsuario';
import { AdminService } from '../../../models/services/admin.service';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-usuarios-admin',
  standalone: true,
  imports: [UsuarioFiltroComponent, UsuarioCardComponent, NgClass],
  templateUrl: './usuarios-admin.component.html',
  styleUrl: './usuarios-admin.component.css'
})
export class UsuariosAdminComponent {

  textoBusqueda: string = '';
  estadoSeleccionado: 'ACTIVO' | 'SUSPENDIDO' = 'ACTIVO';

  onActivadosClick(): void {
    this.estadoSeleccionado = 'ACTIVO';
  }

  onDesactivadosClick(): void {
    this.estadoSeleccionado = 'SUSPENDIDO';
  }

  actualizarTextoBusqueda(texto: string) {
    this.textoBusqueda = texto;
  }
}