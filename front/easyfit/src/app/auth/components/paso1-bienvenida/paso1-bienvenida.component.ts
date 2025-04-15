import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-paso1-bienvenida',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './paso1-bienvenida.component.html',
  styleUrl: './paso1-bienvenida.component.css'
})
export class Paso1BienvenidaComponent {
email: any;
nombre: any;
siguientePaso() {
throw new Error('Method not implemented.');
}

}
