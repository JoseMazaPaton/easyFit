import { Component } from '@angular/core';
import { DashboardService } from '../../../models/services/dashboard.service';
import { ObjetivoService } from '../../../models/services/objetivo.service';
import { ObjetivoResponse } from '../../../models/interfaces/objetivo-response';
import { ResumenDiario } from '../../../models/interfaces/resumen-diario';
import { CaloriasCardComponent } from "../../components/dashboard-user/calorias-card/calorias-card.component";
import { ProgresoPesoCardComponent } from "../../components/dashboard-user/progreso-peso-card/progreso-peso-card.component";
import { HistorialPeso } from '../../../models/interfaces/historial-peso';
import { ConsumoCaloriasSimplesComponent } from "../../components/dashboard-user/consumo-cal-card/consumo-cal-card.component";
import { AuthService } from '../../../models/services/auth.service';

@Component({
  selector: 'app-dashboard-user',
  standalone: true,
  imports: [CaloriasCardComponent, ProgresoPesoCardComponent, ConsumoCaloriasSimplesComponent],
  templateUrl: './dashboard-user.component.html',
  styleUrl: './dashboard-user.component.css'
})
export class DashboardUserComponent {
  nombreUsuario: string = 'Usuario';

  constructor(private authService: AuthService , private dashboardService: DashboardService) {}

  ngOnInit(): void {
    const usuario = this.authService.obtenerUsuario();
    if (usuario && usuario.nombre) {
      this.nombreUsuario = usuario.nombre;
    }

    this.dashboardService.cargarResumenDiario();

    this.dashboardService.refrescarDashboard();

  }

}