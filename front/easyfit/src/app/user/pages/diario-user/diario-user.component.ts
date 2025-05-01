import { Component } from '@angular/core';
import { ComidaService } from '../../../models/services/comida.service';
import { ResumenComidasDiarioComponent } from "../../../shared/components/diario-user/resumen-comidas-diario/resumen-comidas-diario.component";
import { CommonModule, formatDate } from '@angular/common';
import { IComidaDiariaDto } from '../../../models/interfaces/IComidaDiario';
import Swal from 'sweetalert2';
import { ComidaDiarioCardComponent } from "../../../shared/components/diario-user/comida-diario-card/comida-diario-card.component";
import { ActivatedRoute, Router } from '@angular/router';
import { DashboardService } from '../../../models/services/dashboard.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-diario-user',
  standalone: true,
  imports: [CommonModule, ResumenComidasDiarioComponent, ComidaDiarioCardComponent],
  templateUrl: './diario-user.component.html',
  styleUrl: './diario-user.component.css'
})
export class DiarioUserComponent {
  arrayComidas: IComidaDiariaDto[] = [];
  fechaSeleccionada: Date = new Date();
  
  // Datos para el resumen diario
  resumenDiario = {
    kcalObjetivo: 2500,
    kcalConsumidas: 2000,
    kcalRestantes: 500,
    carbohidratosPorcentaje: 50,
    grasasPorcentaje: 25,
    proteinasPorcentaje: 25
  };

  constructor(
    private comidaService: ComidaService,
    private route: ActivatedRoute,
    private router: Router,
    private dashboardService: DashboardService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    // Cuando se abre el componente, recogemos la fecha de los parámetros si existe
    this.route.queryParams.subscribe(params => {
      const fechaParam = params['fecha'];
      this.fechaSeleccionada = fechaParam ? new Date(fechaParam) : new Date();

      this.obtenerComidas();
      this.obtenerResumenDiario();
    });
  }

  // Obtenemos el resumen del día directamente del backend
  obtenerResumenDiario(): void {
    const fechaString = formatDate(this.fechaSeleccionada, 'yyyy-MM-dd', 'en-US');
    
    this.http.get<any>(`http://localhost:9008/dashboard/resumendiario`).subscribe({
      next: (data) => {
        // Guardamos los datos básicos de calorías
        this.resumenDiario.kcalObjetivo = data.kcalObjetivo;
        this.resumenDiario.kcalConsumidas = data.kcalConsumidas;
        this.resumenDiario.kcalRestantes = data.kcalObjetivo - data.kcalConsumidas;
        
        // Calculamos los porcentajes totales de macros basados en los alimentos del día
        this.calcularPorcentajesMacros();
      },
      error: (error) => {
        console.error('Error al obtener resumen diario:', error);
      }
    });
  }

  // Calculamos los porcentajes de macros basados en las comidas del día
  calcularPorcentajesMacros(): void {
    let totalProteinas = 0;
    let totalCarbohidratos = 0;
    let totalGrasas = 0;
    
    // Sumamos todos los macros de cada alimento en cada comida
    this.arrayComidas.forEach(comida => {
      comida.alimentos.forEach(alimento => {
        totalProteinas += alimento.proteinas || 0;
        totalCarbohidratos += alimento.carbohidratos || 0;
        totalGrasas += alimento.grasas || 0;
      });
    });
    
    const totalMacros = totalProteinas + totalCarbohidratos + totalGrasas;
    
    if (totalMacros > 0) {
      // Calculamos los porcentajes redondeados
      this.resumenDiario.proteinasPorcentaje = Math.round((totalProteinas / totalMacros) * 100);
      this.resumenDiario.carbohidratosPorcentaje = Math.round((totalCarbohidratos / totalMacros) * 100);
      this.resumenDiario.grasasPorcentaje = Math.round((totalGrasas / totalMacros) * 100);
      
      // Ajustamos para asegurar que sumen 100%
      const suma = this.resumenDiario.proteinasPorcentaje + 
                  this.resumenDiario.carbohidratosPorcentaje + 
                  this.resumenDiario.grasasPorcentaje;
      
      if (suma !== 100) {
        // Ajustamos el mayor valor para que todo sume 100
        const diff = 100 - suma;
        if (totalProteinas >= totalCarbohidratos && totalProteinas >= totalGrasas) {
          this.resumenDiario.proteinasPorcentaje += diff;
        } else if (totalCarbohidratos >= totalProteinas && totalCarbohidratos >= totalGrasas) {
          this.resumenDiario.carbohidratosPorcentaje += diff;
        } else {
          this.resumenDiario.grasasPorcentaje += diff;
        }
      }
    } else {
      // Valores por defecto si no hay datos
      this.resumenDiario.proteinasPorcentaje = 25;
      this.resumenDiario.carbohidratosPorcentaje = 50;
      this.resumenDiario.grasasPorcentaje = 25;
    }
  }

  // Pedimos al backend las comidas del día seleccionado
  obtenerComidas(): void {
    const fechaString = formatDate(this.fechaSeleccionada, 'yyyy-MM-dd', 'en-US');

    this.comidaService.getComidasDelDia(fechaString).subscribe({
      next: (data) => {
        this.arrayComidas = data;
        // Actualizamos el resumen después de obtener las comidas
        this.calcularPorcentajesMacros();
      },
      error: () => {
        console.error('Error al obtener comidas');
      }
    });
  }

  // Cambia el día y actualiza la URL con la nueva fecha
  cambiarFecha(dias: number): void {
    const nuevaFecha = new Date(this.fechaSeleccionada);
    nuevaFecha.setDate(nuevaFecha.getDate() + dias);

    const fechaFormateada = formatDate(nuevaFecha, 'yyyy-MM-dd', 'en-US');

    this.router.navigate([], {
      queryParams: { fecha: fechaFormateada },
      queryParamsHandling: 'merge'
    });
  }

  // Abre un diálogo para que el usuario escriba el nombre de una nueva comida
  abrirDialogoCrearComida(): void {
    Swal.fire({
      title: 'Nueva comida',
      input: 'text',
      inputLabel: 'Nombre de la comida',
      inputPlaceholder: 'Introduce el nombre',
      showCancelButton: true,
      confirmButtonText: 'Crear'
    }).then(result => {
      const nombre = result.value?.trim();

      if (result.isConfirmed && nombre) {
        const nuevaComida = {
          nombre,
          orden: this.arrayComidas.length + 1,
          fecha: this.fechaSeleccionada
        };

        this.comidaService.crearComida(nuevaComida).subscribe({
          next: () => {
            this.obtenerComidas();
            this.obtenerResumenDiario();
          },
          error: () => {
            Swal.fire('Error', 'No se pudo crear la comida', 'error');
          }
        });
      }
    });
  }
}