import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-paso2-sexo-fecha',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './paso2-sexo-fecha.component.html',
  styleUrl: './paso2-sexo-fecha.component.css'
})
export class Paso2SexoFechaComponent {
sexo: any;
fechaNacimiento: any;
siguientePaso() {
throw new Error('Method not implemented.');
}

}
