import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ObjetivoResponse } from '../interfaces/objetivo-response';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ObjetivoService {

  private apiUrl = `${environment.apiUrl}`;

  constructor(private http: HttpClient) { }

  /**
   * Obtiene los objetivos del usuario (peso, actividad, kcal, macros, etc.)
   */
  getObjetivosUsuario(): Observable<ObjetivoResponse> {
    return this.http.get<ObjetivoResponse>(`${this.apiUrl}/objetivos/misobjetivos`);
  }

  /**
   * Cambia los macros del usuario (en porcentaje).
   */
  actualizarMacros(dto: {
    porcentajeProteinas: number;
    porcentajeCarbohidratos: number;
    porcentajeGrasas: number;
  }): Observable<any> {
    return this.http.put(`${this.apiUrl}/objetivos/macros`, dto);
  }

  /**
   * Cambia el peso actual del usuario.
   */
  actualizarPesoActual(dto: { pesoActual: number }): Observable<any> {
    return this.http.put(`${this.apiUrl}/objetivos/pesoactual`, dto);
  }

  /**
   * Cambia el peso objetivo del usuario.
   */
  actualizarPesoObjetivo(dto: { pesoObjetivo: number }): Observable<any> {
    return this.http.put(`${this.apiUrl}/objetivos/pesometa`, dto);
  }

  /**
   * Cambia el nivel de actividad del usuario.
   */
  actualizarActividad(dto: { actividad: string }): Observable<any> {
    return this.http.put(`${this.apiUrl}/objetivos/nivelactividad`, dto);
  }

  /**
   * Ajusta el ritmo semanal de cambio de peso.
   */
  actualizarMetaSemanal(dto: { opcionPeso: string }): Observable<any> {
    return this.http.put(`${this.apiUrl}/objetivos/metasemanal`, dto);
  }

  /**
   * Establece el objetivo general (mantener, perder, ganar).
   */
  actualizarObjetivoUsuario(dto: { objetivoUsuario: string }): Observable<any> {
    return this.http.put(`${this.apiUrl}/objetivos/metaobjetivo`, dto);
  }
}
