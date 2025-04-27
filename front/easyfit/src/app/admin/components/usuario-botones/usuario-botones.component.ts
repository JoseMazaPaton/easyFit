import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IUsuario } from '../../../models/interfaces/IUsuario';
import { finalize } from 'rxjs';
import { AdminService } from '../../../models/services/admin.service';

@Component({
  selector: 'app-usuario-botones',
  standalone: true,
  imports: [],
  templateUrl: './usuario-botones.component.html',
  styleUrl: './usuario-botones.component.css'
})
export class UsuarioBotonesComponent {

  @Input() usuario!: IUsuario;
  @Output() refresh = new EventEmitter<void>();

  constructor(private adminService: AdminService) {}

  onCambiarEstado(): void {
    if (!this.usuario?.email) {
      console.error('Usuario no válido.');
      return;
    }

    this.adminService.suspenderUsuario(this.usuario.email)
      .subscribe({
        next: () => {
          console.log('Estado de usuario cambiado.');
          this.refresh.emit(); // Emitimos para refrescar la tabla
        },
        error: (error) => {
          console.error('Error al suspender/reactivar usuario:', error);
        }
      });
  }
}