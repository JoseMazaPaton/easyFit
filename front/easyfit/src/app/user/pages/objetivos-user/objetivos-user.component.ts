import { Component } from '@angular/core';
import { ObjetivoResponse } from '../../../models/interfaces/objetivo-response';
import { ObjetivoService } from '../../../models/services/objetivo.service';
import { MacrosCardComponent } from '../../components/objetivo-user/macros-card/macros-card.component';
import { ObjetivoGeneralCardComponent } from '../../components/objetivo-user/objetivo-general-card/objetivo-general-card.component';
import { ActividadObjetivoCardComponent } from '../../components/objetivo-user/actividad-objetivo-card/actividad-objetivo-card.component';
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-objetivos-user',
  standalone: true,
  imports: [MacrosCardComponent, ObjetivoGeneralCardComponent, ActividadObjetivoCardComponent],
  templateUrl: './objetivos-user.component.html',
  styleUrl: './objetivos-user.component.css',
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(10px)' }),
        animate('300ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ])
  ]
})
export class ObjetivosUserComponent {
  
  objetivo: ObjetivoResponse | null = null;
  loading = true;

  constructor(private objetivoService: ObjetivoService) {}

  ngOnInit(): void {
    this.cargarObjetivos();
  }

  cargarObjetivos(): void {
    this.loading = true;
    this.objetivoService.getObjetivosUsuario().subscribe({
      next: (res) => {
        this.objetivo = res;
        this.loading = false;
      },
      error: (err) => {
        console.error('❌ Error al cargar objetivos:', err);
        this.loading = false;
      }
    });
  }

  onActualizado(): void {
    this.cargarObjetivos(); // se vuelve a cargar tras un cambio
  }
}
