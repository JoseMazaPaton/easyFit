import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-paso6-cuenta',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './paso6-cuenta.component.html',
  styleUrl: './paso6-cuenta.component.css'
})
export class Paso6CuentaComponent {
password: any;
email: any;
crearCuenta() {
throw new Error('Method not implemented.');
}

}
