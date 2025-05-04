import { Component} from '@angular/core';
import { UsuarioFiltroComponent } from "../../components/usuarios-admin/usuario-filtro/usuario-filtro.component";
import { NgClass } from '@angular/common';
import { UsuarioCardComponent } from '../../components/usuarios-admin/usuario-card/usuario-card.component';

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