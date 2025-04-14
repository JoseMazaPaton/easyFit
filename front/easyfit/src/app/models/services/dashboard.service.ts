import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResumenDiario } from '../interfaces/resumen-diario';
import { HistorialPeso } from '../interfaces/historial-peso';
import { HistorialCalorias } from '../interfaces/historial-calorias';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private apiUrl = 'http://localhost:9008/dashboard';

  constructor(private http: HttpClient) { }


  /**
   * Obtiene el resumen de calorías del día actual (objetivo, consumidas y restantes).
   */
  getResumenDiario(): Observable<ResumenDiario> {
    return this.http.get<ResumenDiario>(`${this.apiUrl}/resumendiario`);
  }

  /**
   * Devuelve el resumen de pesos (inicial, actual y objetivo) del usuario.
   */
  getResumenPesos(): Observable<HistorialPeso> {
    return this.http.get<HistorialPeso>(`${this.apiUrl}/resumenpesos`);
  }

  /**
   * Devuelve el historial de calorías consumidas durante los últimos 7 días.
   */
  getResumenCalorias7Dias(): Observable<HistorialCalorias[]> {
    return this.http.get<HistorialCalorias[]>(`${this.apiUrl}/resumencalorias`);
  }
}
