import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IUsuarioResumen } from '../../../../models/interfaces/IUsuarioResumen';
import { AdminService } from '../../../../models/services/admin.service';

@Component({
  selector: 'app-card-total-registrados',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card-total-registrados.component.html',
  styleUrl: './card-total-registrados.component.css'
})
export class CardTotalRegistradosComponent {

  resumen: IUsuarioResumen | null = null;
  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.resumen$.subscribe(data => {
      this.resumen = data;
    });
  }
}