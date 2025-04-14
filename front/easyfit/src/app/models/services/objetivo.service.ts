import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ObjetivoResponse } from '../interfaces/objetivo-response';

@Injectable({
  providedIn: 'root'
})
export class ObjetivoService {

  private apiUrl = 'http://localhost:9008/objetivos';

  constructor(private http: HttpClient) { }

  /**
   * Obtiene los objetivos del usuario (peso, actividad, kcal, macros, etc.)
   */
  getObjetivosUsuario(): Observable<ObjetivoResponse> {
    return this.http.get<ObjetivoResponse>(`${this.apiUrl}/misobjetivos`);
  }

  /**
   * Cambia los macros del usuario (en porcentaje).
   */
  actualizarMacros(macrosDto: {
    porcentajeProteinas: number;
    porcentajeCarbohidratos: number;
    porcentajeGrasas: number;
  }): Observable<any> {
    return this.http.put(`${this.apiUrl}/macros`, macrosDto);
  }

  /**
   * Cambia el peso actual del usuario.
   */
  actualizarPesoActual(peso: number): Observable<ObjetivoResponse> {
    return this.http.put<ObjetivoResponse>(`${this.apiUrl}/pesoactual`, {
      pesoActual: peso
    });
  }

  /**
   * Cambia el peso objetivo del usuario.
   */
  actualizarPesoObjetivo(peso: number): Observable<ObjetivoResponse> {
    return this.http.put<ObjetivoResponse>(`${this.apiUrl}/pesometa`, {
      pesoObjetivo: peso
    });
  }

  /**
   * Cambia el nivel de actividad del usuario.
   */
  actualizarActividad(actividad: 'SEDENTARIO' | 'LIGERO' | 'MODERADO' | 'ACTIVO' | 'MUY_ACTIVO'): Observable<ObjetivoResponse> {
    return this.http.put<ObjetivoResponse>(`${this.apiUrl}/nivelactividad`, {
      actividad
    });
  }

  /**
   * Ajusta el ritmo semanal de cambio de peso.
   */
  actualizarMetaSemanal(opcionPeso: 'GANAR_RAPIDO' | 'GANAR_LENTO' | 'MANTENER' | 'PERDER_LENTO' | 'PERDER_RAPIDO'): Observable<ObjetivoResponse> {
    return this.http.put<ObjetivoResponse>(`${this.apiUrl}/metasemanal`, {
      opcionPeso
    });
  }

  /**
   * Establece el objetivo general (mantener, perder, ganar).
   */
  actualizarObjetivoUsuario(objetivoUsuario: 'PERDER' | 'MANTENER' | 'GANAR'): Observable<ObjetivoResponse> {
    return this.http.put<ObjetivoResponse>(`${this.apiUrl}/metaobjetivo`, {
      objetivoUsuario
    });
  }
}
