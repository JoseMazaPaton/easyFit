import { Component } from '@angular/core';
import { ComidaService } from '../../../models/services/comida.service';
import { ResumenComidasDiarioComponent } from "../../components/diario-user/resumen-comidas-diario/resumen-comidas-diario.component";
import { CommonModule, formatDate } from '@angular/common';
import { IComidaDiariaDto } from '../../../models/interfaces/IComidaDiario';
import Swal from 'sweetalert2';
import { ComidaDiarioCardComponent } from "../../components/diario-user/comida-diario-card/comida-diario-card.component";
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

  // Obtenemos el valor de calorías objetivo del usuario desde el backend
  obtenerResumenDiario(): void {    
    this.dashboardService.resumenDiario$.subscribe({
      next: (data) => {
        // Solo obtenemos el objetivo de calorías del backend
        if (data) {
          this.resumenDiario.kcalObjetivo = data.kcalObjetivo;
        }
        
        // El resto lo calculamos a partir de las comidas del día seleccionado
        this.calcularResumenDiarioCompleto();
      },
      error: (error) => {
        console.error('Error al obtener resumen diario:', error);
      }
    });
  }

  // Calculamos todo el resumen directamente a partir de los alimentos en las comidas del día
  calcularResumenDiarioCompleto(): void {
    // Reiniciamos contadores
    this.resumenDiario.kcalConsumidas = 0;
    let totalProteinas = 0;
    let totalCarbohidratos = 0;
    let totalGrasas = 0;
    
    // Variable para verificar si hay al menos un alimento
    let hayAlimentos = false;
    
    // Sumamos todos los valores de cada alimento en cada comida
    this.arrayComidas.forEach(comida => {
      if (comida.alimentos && comida.alimentos.length > 0) {
        hayAlimentos = true;
        comida.alimentos.forEach(alimento => {
          // Sumamos calorías
          this.resumenDiario.kcalConsumidas += alimento.kcal || 0;
          
          // Sumamos macronutrientes
          totalProteinas += alimento.proteinas || 0;
          totalCarbohidratos += alimento.carbohidratos || 0;
          totalGrasas += alimento.grasas || 0;
        });
      }
    });
    
    // Calculamos los restantes
    this.resumenDiario.kcalRestantes = this.resumenDiario.kcalObjetivo - this.resumenDiario.kcalConsumidas;
    
    // Si no hay alimentos, establecemos los macronutrientes a 0
    if (!hayAlimentos) {
      this.resumenDiario.proteinasPorcentaje = 0;
      this.resumenDiario.carbohidratosPorcentaje = 0;
      this.resumenDiario.grasasPorcentaje = 0;
      return;
    }
    
    // Calculamos porcentajes de macros
    const totalMacros = totalProteinas + totalCarbohidratos + totalGrasas;
    
    if (totalMacros > 0) {
      // Porcentajes redondeados
      this.resumenDiario.proteinasPorcentaje = Math.round((totalProteinas / totalMacros) * 100);
      this.resumenDiario.carbohidratosPorcentaje = Math.round((totalCarbohidratos / totalMacros) * 100);
      this.resumenDiario.grasasPorcentaje = Math.round((totalGrasas / totalMacros) * 100);
      
      // Ajustamos para que sumen 100%
      const suma = this.resumenDiario.proteinasPorcentaje + 
                  this.resumenDiario.carbohidratosPorcentaje + 
                  this.resumenDiario.grasasPorcentaje;
      
      if (suma !== 100 && suma > 0) {
        // Ajustamos el mayor valor
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
      // Valores a 0 si no hay macros
      this.resumenDiario.proteinasPorcentaje = 0;
      this.resumenDiario.carbohidratosPorcentaje = 0;
      this.resumenDiario.grasasPorcentaje = 0;
    }
  }

  // Pedimos al backend las comidas del día seleccionado
  obtenerComidas(): void {
    const fechaString = formatDate(this.fechaSeleccionada, 'yyyy-MM-dd', 'en-US');

    this.comidaService.getComidasDelDia(fechaString).subscribe({
      next: (data) => {
        this.arrayComidas = data;
        // Actualizamos el resumen con las comidas obtenidas
        this.calcularResumenDiarioCompleto();
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
            // El resumen se actualizará automáticamente en obtenerComidas()
          },
          error: () => {
            Swal.fire('Error', 'No se pudo crear la comida', 'error');
          }
        });
      }
    });
  }
}