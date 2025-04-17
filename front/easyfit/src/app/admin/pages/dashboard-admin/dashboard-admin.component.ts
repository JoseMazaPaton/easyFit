import { Component } from '@angular/core';
import { CardTotalRegistradosComponent } from "../../components/card-total-registrados/card-total-registrados.component";
import { CardUsuariosGeneroComponent } from "../../components/card-usuarios-genero/card-usuarios-genero.component";
import { CardUsuariosMesesComponent } from "../../components/card-usuarios-meses/card-usuarios-meses.component";
import { AdminService } from '../../../models/services/admin.service';

@Component({
  selector: 'app-dashboard-admin',
  standalone: true,
  imports: [CardTotalRegistradosComponent, CardUsuariosGeneroComponent, CardUsuariosMesesComponent],
  templateUrl: './dashboard-admin.component.html',
  styleUrl: './dashboard-admin.component.css'
})
export class DashboardAdminComponent {

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.cargarResumen(); // 👈 Esto es CLAVE
  }
}