import { Component, inject } from '@angular/core';
import { AuthService } from '../../models/services/auth.service';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { ILoginUsuario } from '../../models/interfaces/ILoginUsuario';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive,],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})

export class NavbarComponent {

  private readonly authService: AuthService = inject(AuthService);

  role: string = '';
  usuario!: ILoginUsuario;

  ngOnInit(): void {

    this.role = this.authService.obtenerRol();
    this.usuario = this.authService.obtenerUsuario(); 
    console.log('[Navbar] Rol detectado:', this.role);
    console.log('[Navbar] Usuario:', this.usuario);
  }

  logOut(): void {
    this.authService.logout();
  }
}
