import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { IComidaDiariaDto } from '../interfaces/IComidaDiario';
import { IResumenComida } from '../interfaces/IResumenComida';
import { IAgregarAlimento } from '../interfaces/IAgregarAlimento';
import { IAlimentoCantidad } from '../interfaces/IAlimentoCantidad';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ComidaService {

  private apiUrl= `${environment.apiUrl}/comidas`;

  private comidasSubject = new BehaviorSubject<IComidaDiariaDto[]>([]);
  public comidas$ = this.comidasSubject.asObservable();

  constructor(private http: HttpClient) {}

  // Obtener comidas del día
  getComidasDelDia(fecha: string): Observable<IComidaDiariaDto[]> {
    return this.http.get<IComidaDiariaDto[]>(`${this.apiUrl}/fecha`, {
      params: { fecha }
    }).pipe(
      tap((comidas) => this.comidasSubject.next(comidas))
    );
  }

  // Crear comida
  crearComida(comida: Partial<IComidaDiariaDto>): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, comida).pipe(
      tap(() => this.refreshComidas())
    );
  }

  // Añadir alimento a comida
  agregarAlimentoAComida(idComida: number, request: IAgregarAlimento): Observable<any> {
    return this.http.post(`${this.apiUrl}/${idComida}/añadirAlimento`, request).pipe(
      tap(() => this.refreshComidas())
    );
  }

  // Eliminar alimento de comida
  eliminarAlimentoDeComida(idComida: number, idAlimento: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${idComida}/alimentos/${idAlimento}`).pipe(
      tap(() => this.refreshComidas())
    );
  }

  // Actualizar cantidad de alimento
  actualizarCantidadAlimento(idComida: number, idAlimento: number, cantidad: number): Observable<any> {
    const body: IAlimentoCantidad = { cantidad };
    return this.http.put(`${this.apiUrl}/${idComida}/alimentos/${idAlimento}`, body).pipe(
      tap(() => this.refreshComidas())
    );
  }

  // Eliminar comida completa
  eliminarComida(idComida: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${idComida}`).pipe(
      tap(() => this.refreshComidas())
    );
  }

  // Obtener resumen de una comida
  getResumenComida(idComida: number): Observable<IResumenComida> {
    return this.http.get<IResumenComida>(`${this.apiUrl}/${idComida}/resumen`);
  }

  // Refrescar comidas (se puede llamar tras añadir/eliminar)
  private refreshComidas() {
    const fechaHoy = new Date().toISOString().split('T')[0];
    this.getComidasDelDia(fechaHoy).subscribe(); // el tap interno actualizará el subject
  }
}