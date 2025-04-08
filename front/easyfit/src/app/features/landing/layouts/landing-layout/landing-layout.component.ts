import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavBarLandingComponent } from '../../components/nav-bar-landing/nav-bar-landing.component';
import { FooterLandingComponent } from '../../components/footer-landing/footer-landing.component';

@Component({
  standalone: true,
  selector: 'app-landing-layout',
  imports: [RouterOutlet, NavBarLandingComponent, FooterLandingComponent],
  templateUrl: './landing-layout.component.html',
  styleUrl: './landing-layout.component.css',
})
export class LandingLayoutComponent {}
