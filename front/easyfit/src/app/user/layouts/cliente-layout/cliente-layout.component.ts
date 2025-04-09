import { Component } from '@angular/core';
import { NavbarComponent } from "../../../components/navbar/navbar.component";
import { FooterComponent } from "../../../components/footer/footer.component";
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-cliente-layout',
  standalone: true,
  imports: [NavbarComponent, FooterComponent, RouterOutlet],
  templateUrl: './cliente-layout.component.html',
  styleUrl: './cliente-layout.component.css'
})
export class ClienteLayoutComponent {

}
