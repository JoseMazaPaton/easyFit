import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavBarLandingComponent } from "../../../public/components/nav-bar-landing/nav-bar-landing.component";

@Component({
  selector: 'app-access-admin',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './access-admin.component.html',
  styleUrl: './access-admin.component.css'
})
export class AccessAdminComponent {

}
