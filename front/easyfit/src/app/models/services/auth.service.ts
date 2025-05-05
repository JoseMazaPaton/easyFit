
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { ILoginUsuario } from '../interfaces/ILoginUsuario';
import { IRegistroUsuario } from '../interfaces/IRegistroUsuario';
import { environment } from '../../../environments/environment';



@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  private baseUrl = `${environment.apiUrl}/auth`; // ruta del backend

  constructor(private http: HttpClient, private router: Router) {}

  login(email: string, password: string): Observable<ILoginUsuario> {
    return this.http.post<ILoginUsuario>(`${this.baseUrl}/login`, { email, password });
  }

  //Metodo para registrar un nuevo usuario
  registrarUsuario(usuarioRegistro: IRegistroUsuario): Observable<IRegistroUsuario> {
      return this.http.post<IRegistroUsuario>(`${this.baseUrl}/registro`, usuarioRegistro);
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

  comprobarEmail(email: string): Observable<boolean> {
    return this.http.get<{ disponible: boolean }>(`${this.baseUrl}/comprobaremail`, {
      params: { email }
    }).pipe(map(res => res.disponible));
  }
}
