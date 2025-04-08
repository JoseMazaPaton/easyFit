import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-nav-bar-landing',
  imports: [RouterLink],
  templateUrl: './nav-bar-landing.component.html',
  styleUrl: './nav-bar-landing.component.css',
})
export class NavBarLandingComponent {}
