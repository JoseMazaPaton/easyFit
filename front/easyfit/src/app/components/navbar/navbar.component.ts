import { Component, EventEmitter, inject, Output } from '@angular/core';
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

  @Output() linkClicked = new EventEmitter<void>(); // 👈 Añadir esto

  role: string = '';
  usuario!: ILoginUsuario;

  ngOnInit(): void {
    this.role = this.authService.obtenerRol();
    this.usuario = this.authService.obtenerUsuario(); 
  }

  logOut(): void {
    this.authService.logout();
  }

  // 👇 Método para emitir evento desde los enlaces
  onNavLinkClick() {
    this.linkClicked.emit();
  }
}
