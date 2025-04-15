import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-paso3-objetivo-genera',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './paso3-objetivo-genera.component.html',
  styleUrl: './paso3-objetivo-genera.component.css'
})
export class Paso3ObjetivoGeneraComponent {
objetivo: any;
actividad: any;
siguientePaso() {
throw new Error('Method not implemented.');
}

}
