import { Component, inject } from '@angular/core';
import { AuthService } from '../../models/services/auth.service';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { UserInfoCardComponent } from "../../shared/components/user-info-card/user-info-card.component";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, UserInfoCardComponent],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})

export class NavbarComponent {

  AuthService = inject(AuthService)
  usuario = JSON.parse(localStorage.getItem('usuario') || '{}');

  constructor() {}




logOut() {
  this.AuthService.logout()
}

}
