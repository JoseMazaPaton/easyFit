
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { UserLogin } from '../interfaces/UserLogin';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  // private http = inject(HttpClient);
  private apiUrl = 'http://localhost:9008/auth/';

  constructor(private http: HttpClient, private router: Router) {}

  login(email: string, password: string): Observable<UserLogin> {
    return this.http.post<UserLogin>(this.apiUrl + 'login', { email, password }).pipe(
      tap((res) => {
        localStorage.setItem('token', res.token); 
        localStorage.setItem('email', res.email);
      })
    );
  }

  // registro(register: Registro): Observable<UserLogin> {
  //       return this.http.post<Registro>(this.apiUrl + 'registro', register);
  // }

  // logOut(): void {
  //   localStorage.removeItem(this.tokenKey);
  //   this.router.navigate(['']);
  // }

  // getToken(): string | null {
  //   return localStorage.getItem(this.tokenKey);
  // }

  // isAuthenticated(): boolean {
  //   const token = this.getToken();
  //   if (!token) return false;

  //   try {
  //     const payload = JSON.parse(atob(token.split('.')[1]));
  //     const exp = payload.exp;
  //     if (!exp) return true; // si no tiene expiración, consideramos válido

  //     const now = Math.floor(Date.now() / 1000);
  //     return exp > now;
  //   } catch (error) {
  //     console.error('Token inválido:', error);
  //     return false;
  //   }
  // }
}
