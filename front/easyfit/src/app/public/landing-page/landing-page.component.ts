import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FooterLandingComponent } from "../components/footer-landing/footer-landing.component";
import { NavBarLandingComponent } from "../components/nav-bar-landing/nav-bar-landing.component";

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [RouterModule, NavBarLandingComponent, FooterLandingComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css',
})
export class LandingPageComponent {}
