import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, HostListener, ViewChild } from '@angular/core';
import { NavbarComponent } from "../../../components/navbar/navbar.component";
import { FooterComponent } from "../../../components/footer/footer.component";
import { RouterOutlet } from '@angular/router';
import { animate, group, query, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-cliente-layout',
  standalone: true,
  imports: [NavbarComponent, RouterOutlet],
  templateUrl: './cliente-layout.component.html',
  styleUrl: './cliente-layout.component.css',
  animations: [
    trigger('slideInOut', [
      state('in', style({
        transform: 'translateX(0%)',
        opacity: 1,
        visibility: 'visible'
      })),
      state('out', style({
        transform: 'translateX(-100%)',
        opacity: 0,
        visibility: 'hidden'
      })),
      transition('out => in', animate('200ms ease-out')),
      transition('in => out', animate('200ms ease-in'))
    ]),
    trigger('routeAnimations', [
      transition('* <=> *', [
        style({ position: 'relative' }),
        query(':enter, :leave', [
          style({
            position: 'absolute',
            width: '100%'
          })
        ], { optional: true }),
        group([
          query(':leave', [
            animate('150ms ease-out', style({ opacity: 0 }))
          ], { optional: true }),
          query(':enter', [
            style({ opacity: 0 }),
            animate('150ms ease-in', style({ opacity: 1 }))
          ], { optional: true })
        ])
      ])
    ])
  ]
})
export class ClienteLayoutComponent implements AfterViewInit {

  navbarVisible = true;
  isDesktop = true;

  @ViewChild('navbar') navbarRef?: ElementRef;
  @ViewChild('hamburgerButton') buttonRef?: ElementRef;

  constructor(private cdr: ChangeDetectorRef) {}

  ngAfterViewInit(): void {
  this.cdr.detectChanges(); // ✅ evita NG0100
}

  ngOnInit() {
    this.checkScreenSize();
  }

  @HostListener('window:resize')
  onResize() {
    this.checkScreenSize();
  }

  checkScreenSize() {
    this.isDesktop = window.innerWidth >= 768;
    this.navbarVisible = this.isDesktop;
  }

  toggleNavbar() {
    this.navbarVisible = !this.navbarVisible;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const clickedInsideNavbar = this.navbarRef?.nativeElement.contains(event.target);
    const clickedOnButton = this.buttonRef?.nativeElement.contains(event.target);

    if (!this.isDesktop && this.navbarVisible && !clickedInsideNavbar && !clickedOnButton) {
      this.navbarVisible = false;
    }
  }

  onNavLinkClick() {
    if (!this.isDesktop) {
      this.navbarVisible = false;
    }
  }
  getRouteAnimationData(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData?.['animation'];
  }
}
