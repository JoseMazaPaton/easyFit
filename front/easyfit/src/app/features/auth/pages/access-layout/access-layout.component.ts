import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavBarLandingComponent } from "../../../landing/components/nav-bar-landing/nav-bar-landing.component";
import { FooterLandingComponent } from "../../../landing/components/footer-landing/footer-landing.component";

@Component({
  standalone: true,
  selector: 'app-access-layout',
  imports: [RouterOutlet, NavBarLandingComponent, FooterLandingComponent],
  templateUrl: './access-layout.component.html',
  styleUrl: './access-layout.component.css',
})
export class AccessLayoutComponent {}
