import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ResumenDiario } from '../interfaces/resumen-diario';
import { HistorialPeso } from '../interfaces/historial-peso';
import { HistorialCalorias } from '../interfaces/historial-calorias';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private apiUrl = `${environment.apiUrl}/dashboard`;

  constructor(private http: HttpClient) {}

  // 📌 1. Resumen Diario
  private resumenDiarioSubject = new BehaviorSubject<ResumenDiario | null>(null);
  public resumenDiario$ = this.resumenDiarioSubject.asObservable();

  cargarResumenDiario(): void {
    this.http.get<ResumenDiario>(`${this.apiUrl}/resumendiario`).subscribe({
      next: (data) => this.resumenDiarioSubject.next(data),
      error: (err) => {
        console.error('[Resumen Diario] Error:', err);
        this.resumenDiarioSubject.next(null);
      }
    });
  }

  // 📌 2. Resumen Pesos
  private resumenPesoSubject = new BehaviorSubject<HistorialPeso | null>(null);
  public resumenPeso$ = this.resumenPesoSubject.asObservable();

  cargarResumenPeso(): void {
    this.http.get<HistorialPeso>(`${this.apiUrl}/resumenpesos`).subscribe({
      next: (data) => this.resumenPesoSubject.next(data),
      error: (err) => {
        console.error('[Resumen Pesos] Error:', err);
        this.resumenPesoSubject.next(null);
      }
    });
  }

  // 📌 3. Historial Calorías
  private calorias7diasSubject = new BehaviorSubject<HistorialCalorias[] | null>(null);
  public calorias7dias$ = this.calorias7diasSubject.asObservable();

  cargarCalorias7Dias(): void {
    this.http.get<HistorialCalorias[]>(`${this.apiUrl}/resumencalorias`).subscribe({
      next: (data) => this.calorias7diasSubject.next(data),
      error: (err) => {
        console.error('[Calorías 7 días] Error:', err);
        this.calorias7diasSubject.next(null);
      }
    });
  }

  // 🔁 Cargar todos juntos
  refrescarDashboard(): void {
    this.cargarResumenDiario();
    this.cargarResumenPeso();
    this.cargarCalorias7Dias();
  }

  // 🔍 Acceso directo a último valor (opcional)
  get resumenActual(): ResumenDiario | null {
    return this.resumenDiarioSubject.value;
  }

  get pesoActual(): HistorialPeso | null {
    return this.resumenPesoSubject.value;
  }

  get caloriasActual(): HistorialCalorias[] | null {
    return this.calorias7diasSubject.value;
  }
}