import { Component, inject } from '@angular/core';
import { AuthService } from '../../models/services/auth.service';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink,RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})

export class NavbarComponent {

  AuthService = inject(AuthService)

  constructor() {}

logOut() {
  this.AuthService.logout()
}

}
