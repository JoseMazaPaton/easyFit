import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-paso5-datos-fisicos',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './paso5-datos-fisicos.component.html',
  styleUrl: './paso5-datos-fisicos.component.css'
})
export class Paso5DatosFisicosComponent {
pesoDeseado: any;
pesoActual: any;
altura: any;
siguientePaso: any;

}
