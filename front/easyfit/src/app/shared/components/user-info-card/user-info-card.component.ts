import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-user-info-card',
  standalone: true,
  imports: [],
  templateUrl: './user-info-card.component.html',
  styleUrl: './user-info-card.component.css'
})
export class UserInfoCardComponent {
  @Input() nombre: string = 'Nombre de Usuario';
  @Input() rol: string = 'Usuario';
  @Input() avatarUrl: string = '/assets/images/profile.png';
}
