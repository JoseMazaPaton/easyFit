import { Component } from '@angular/core';
import { AdminService } from '../../../models/services/admin.service';
import { CardTotalRegistradosComponent } from '../../components/dashboard-admin/card-total-registrados/card-total-registrados.component';
import { CardUsuariosGeneroComponent } from "../../components/dashboard-admin/card-usuarios-genero/card-usuarios-genero.component";
import { CardUsuariosMesesComponent } from "../../components/dashboard-admin/card-usuarios-meses/card-usuarios-meses.component";

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
    this.adminService.cargarResumen();
  }
}