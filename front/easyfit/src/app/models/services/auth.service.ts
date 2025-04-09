
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { ILoginUsuario } from '../interfaces/ILoginUsuario';
import { RegisterUser } from '../interfaces/registerUser';
import { RegisterResponse } from '../interfaces/register-response';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:9008/auth'; // ruta del backend

  constructor(private http: HttpClient, private router: Router) {}

  login(email: string, password: string): Observable<ILoginUsuario> {
    return this.http.post<ILoginUsuario>(`${this.baseUrl}/login`, { email, password });
  }

  // Nuevo método para registrar un usuario
  register(userData: RegisterUser): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.baseUrl}/register`, userData).pipe(
      // Si el backend te devuelve token y usuario tras el registro, podrías almacenarlos aquí
      tap(response => {
        if (response.token && response.usuario) {
          this.guardarUsuarioYToken(response.token, response.usuario);
        }
      })
    );
  }

  guardarUsuarioYToken(token: string, usuario: any) {
    localStorage.setItem('token', token);
    localStorage.setItem('usuario', JSON.stringify(usuario));
  }

  obtenerToken(): string | null {
    return localStorage.getItem('token');
  }

  obtenerUsuario(): any {
    const user = localStorage.getItem('usuario');
    return user ? JSON.parse(user) : null;
  }

  estaLogueado(): boolean {
    return !!this.obtenerToken();
  }

  logout() {
    //Usamos la ruta del backen para cerrar la sesion alli tambien
    this.http.post(`${this.baseUrl}/logout`, {}).subscribe({
      next: () => {
        //limpiamos el token y el usuario
        localStorage.removeItem('token');
        localStorage.removeItem('usuario');
        //redireccionamos al landing de la web
        this.router.navigate(['']);
      },
      error: (err) => {
        console.error('Error al cerrar sesión en el backend:', err);
        // Aún así redirige al login aunque no se cierre correctamente en el backend
        localStorage.removeItem('token');
        localStorage.removeItem('usuario');
        this.router.navigate(['/landing']);
      }
    });
  }

  obtenerRol(): string {
    const usuario = this.obtenerUsuario();
    return usuario?.rol || '';
  }
}
