import { Objetivo } from './../../interfaces/objetivo';
import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, tap } from 'rxjs';
import { Login } from '../../interfaces/login';
import { Usuario } from '../../interfaces/usuario';
import { Registro } from '../../interfaces/registro';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // private http = inject(HttpClient);
  private apiUrl = 'http://localhost:9008/auth/';
  private tokenKey = 'token';


  constructor(private http: HttpClient,
              private router: Router
  ) { }

  login(user: Login): Observable<Login> {
    return this.http.post<Login>(this.apiUrl + "login", user).pipe(
      tap((response) => {
        if (response.token) {
          localStorage.setItem(this.tokenKey, response.token);
        }
      })
    );
  }

  registro(register: Registro): Observable<Registro> {
    return this.http.post<Registro>(this.apiUrl + 'registro', register);
  }

  logOut(): void {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token) return false;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const exp = payload.exp;
      if (!exp) return true; // si no tiene expiración, consideramos válido
  
      const now = Math.floor(Date.now() / 1000);
      return exp > now;
    } catch (error) {
      console.error('Token inválido:', error);
      return false;
    }
  }
    
}
