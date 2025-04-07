import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth.guard';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegistroComponent } from './features/auth/registro/registro.component';
import { LandingComponent } from './features/landing/landing.component';

export const routes: Routes = [
  { path: "", 
      pathMatch: 'full', 
      redirectTo: "landing" 
  },
  { path: "landing",
    component: AuthLayoutComponent,
    children: [
      { path: "", component: LandingComponent},
      { path: "login", component: LoginComponent},
      { path: "registro", component: RegistroComponent}
    ]
  },
  {
    path: 'dashboard',
    component: MainLayoutComponent, // ← Layout con sidebar, header, etc.
    canActivate: [authGuard],       // ← Solo si está logueado
  },
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  {
        path: '**',
        redirectTo: 'login'
  }
];
