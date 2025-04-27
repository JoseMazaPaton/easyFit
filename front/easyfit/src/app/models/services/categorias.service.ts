import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Categoria } from '../interfaces/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriasService {
  private apiUrl = 'http://localhost:9008/categorias';

  private categoriasSubject = new BehaviorSubject<Categoria[]>([]);
  public categorias$ = this.categoriasSubject.asObservable();

  constructor(private http: HttpClient) {}

  cargarCategorias(): void {
    this.http.get<Categoria[]>(`${this.apiUrl}/todas`).subscribe({
      next: (categorias) => this.categoriasSubject.next(categorias),
      error: (err) => console.error('Error cargando categorías', err)
    });
  }

  crearCategoria(categoria: Categoria): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, categoria).pipe(
      tap(() => this.cargarCategorias())
    );
  }

  modificarCategoria(idCategoria: number, categoria: Categoria): Observable<any> {
    return this.http.put(`${this.apiUrl}/modificar/${idCategoria}`, categoria).pipe(
      tap(() => this.cargarCategorias())
    );
  }

  eliminarCategoria(idCategoria: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${idCategoria}`).pipe(
      tap(() => this.cargarCategorias())
    );
  }
}