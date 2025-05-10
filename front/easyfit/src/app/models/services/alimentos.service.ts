import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Alimento } from '../interfaces/alimento';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlimentosService {

  private apiUrl = `${environment.apiUrl}/alimentos`;

  constructor(private http: HttpClient) { }

  /**
   * Obtener todos los alimentos, con filtro opcional.
   */
  getAlimentos(search?: string): Observable<Alimento[]> {
    let params = new HttpParams();
    if (search) {
      params = params.set('search', search);
    }
    return this.http.get<Alimento[]>(this.apiUrl, { params });
  }


  /**
   * Prueba despliegue
   */


  /**
   * Obtener los alimentos creados por el usuario.
   */
  getMisAlimentos(): Observable<Alimento[]> {
    return this.http.get<Alimento[]>(`${this.apiUrl}/mis`);
  }

   /**
   * Crear un alimento personalizado.
   */
   crearAlimento(alimento: Alimento): Observable<string> {
    return this.http.post(`${this.apiUrl}`, alimento, { responseType: 'text' });
  }

  /**
   * Modificar un alimento (solo si lo creó el usuario).
   */
  actualizarAlimento(id: number, alimento: Alimento): Observable<string> {
    return this.http.put(`${this.apiUrl}/${id}`, alimento, { responseType: 'text' });
  }

  /**
   * Eliminar un alimento (solo si lo creó el usuario).
   */
  eliminarAlimento(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }

}
