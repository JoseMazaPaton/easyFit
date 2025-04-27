import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { IUsuarioResumen } from '../interfaces/IUsuarioResumen';
import { IUsuario } from '../interfaces/IUsuario';
import { Categoria } from '../interfaces/categoria';
import { IAlimento } from '../interfaces/IAlimento';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  // Ajusta esto a tu backend real
  private readonly apiUrl = 'http://localhost:9008/admin/';

  constructor(private http: HttpClient) {}

  // --- DASHBOARD ---
  private resumenSubject = new BehaviorSubject<IUsuarioResumen | null>(null);
  public resumen$ = this.resumenSubject.asObservable();

  cargarResumen(): void {
    this.http.get<IUsuarioResumen>(`${this.apiUrl}dashboard/resumen`).subscribe({
      next: (resumen) => this.resumenSubject.next(resumen),
      error: () => this.resumenSubject.next(null)
    });
  }

  refrescarResumen(): void {
    this.cargarResumen();
  }

  getResumenActual(): IUsuarioResumen | null {
    return this.resumenSubject.value;
  }

  // --- USUARIOS ---
  private usuariosSubject = new BehaviorSubject<IUsuario[]>([]);
  public usuarios$ = this.usuariosSubject.asObservable();

  obtenerTodosUsuarios(): void {
    this.http.get<IUsuario[]>(`${this.apiUrl}usuarios`).subscribe({
      next: (usuarios) => this.usuariosSubject.next(usuarios),
      error: () => this.usuariosSubject.next([])
    });
  }

  obtenerUsuariosPorEmail(email: string): void {
    this.http.get<IUsuario[]>(`${this.apiUrl}usuarios/${email}`).subscribe({
      next: (usuarios) => this.usuariosSubject.next(usuarios),
      error: () => this.usuariosSubject.next([])
    });
  }

  obtenerUsuariosPorSexo(sexo: string): void {
    this.http.get<IUsuario[]>(`${this.apiUrl}usuarios/sexo/${sexo}`).subscribe({
      next: (usuarios) => this.usuariosSubject.next(usuarios),
      error: () => this.usuariosSubject.next([])
    });
  }

  obtenerUsuariosPorEdad(edad: number): void {
    this.http.get<IUsuario[]>(`${this.apiUrl}usuarios/edad/${edad}`).subscribe({
      next: (usuarios) => this.usuariosSubject.next(usuarios),
      error: () => this.usuariosSubject.next([])
    });
  }


  suspenderUsuario(email: string): Observable<any> {
    return this.http.put(`${this.apiUrl}usuarios/${email}/suspender`, null);
  }



}