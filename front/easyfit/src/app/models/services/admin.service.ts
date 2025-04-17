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

  // Observable reactivo para compartir datos con los componentes
  private resumenSubject = new BehaviorSubject<IUsuarioResumen| null>(null);
  public resumen$ = this.resumenSubject.asObservable();

  // Ajusta esto a tu backend real
  private readonly apiUrl = 'http://localhost:9008/admin/';

  constructor(private http: HttpClient) {}

  // --- DASHBOARD ---
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
  obtenerUsuariosPorEmail(email: string): Observable<IUsuario[]> {
    return this.http.get<IUsuario[]>(`${this.apiUrl}usuarios/${email}`);
  }

  obtenerUsuariosPorSexo(sexo: string): Observable<IUsuario[]> {
    return this.http.get<IUsuario[]>(`${this.apiUrl}usuarios/sexo/${sexo}`);
  }

  obtenerUsuariosPorEdad(edad: number): Observable<IUsuario[]> {
    return this.http.get<IUsuario[]>(`${this.apiUrl}usuarios/edad/${edad}`);
  }

  suspenderUsuario(email: string): Observable<any> {
    return this.http.put(`${this.apiUrl}usuarios/${email}/suspender`, null);
  }

  // --- CATEGORÍAS ---
  crearCategoria(categoria: Categoria): Observable<any> {
    return this.http.post(`${this.apiUrl}categorias/crear`, categoria);
  }

  modificarCategoria(idCategoria: number, categoria: Categoria): Observable<any> {
    return this.http.put(`${this.apiUrl}categorias/modificar/${idCategoria}`, categoria);
  }

  eliminarCategoria(idCategoria: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}categorias/eliminar/${idCategoria}`);
  }

  // --- ALIMENTOS ---
  crearAlimento(alimento: IAlimento): Observable<any> {
    return this.http.post(`${this.apiUrl}alimentos/crear`, alimento);
  }

  modificarAlimento(idAlimento: number, alimento: IAlimento): Observable<any> {
    return this.http.put(`${this.apiUrl}alimentos/modificar/${idAlimento}`, alimento);
  }

  eliminarAlimento(idAlimento: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}alimentos/eliminar/${idAlimento}`);
  }
}