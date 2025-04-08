import { Component } from '@angular/core';
import { NavBarLandingComponent } from "../../../public/components/nav-bar-landing/nav-bar-landing.component";
import { FooterLandingComponent } from "../../../public/components/footer-landing/footer-landing.component";
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-access-layout',
  standalone: true,
  imports: [RouterOutlet, NavBarLandingComponent, FooterLandingComponent],
  templateUrl: './access-layout.component.html',
  styleUrl: './access-layout.component.css'
})
export class AccessLayoutComponent {

}
