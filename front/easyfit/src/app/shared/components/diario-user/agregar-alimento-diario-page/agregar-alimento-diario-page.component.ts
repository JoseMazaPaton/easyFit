import { Component, Input } from '@angular/core';
import { Alimento } from '../../../../models/interfaces/alimento';
import { AlimentosService } from '../../../../models/services/alimentos.service';
import { AlimentosListaComponent } from "../../alimentos-user/alimentos-lista/alimentos-lista.component";
import { AlimentosFiltroComponent } from "../../alimentos-user/alimentos-filtro/alimentos-filtro.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ComidaService } from '../../../../models/services/comida.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';


@Component({
  selector: 'app-agregar-alimento-diario-page',
  standalone: true,
  imports: [AlimentosListaComponent, AlimentosFiltroComponent, CommonModule ,FormsModule],
  templateUrl: './agregar-alimento-diario-page.component.html',
  styleUrl: './agregar-alimento-diario-page.component.css'
})
export class AgregarAlimentoDiarioPageComponent {
  
  alimentos: Alimento[] = [];                // Lista de alimentos que se muestran al buscar
  alimentoSeleccionado: Alimento | null = null; // Alimento que selecciona el usuario
  porciones: number = 1;                     // Número de porciones elegidas
  idComida!: number;                         // ID de la comida que estamos editando
  fechaSeleccionada: string = '';            // Fecha para volver al día correcto

  constructor(
    private alimentosService: AlimentosService,
    private comidaService: ComidaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Obtenemos el id de la comida desde la URL
    this.route.params.subscribe(params => {
      this.idComida = +params['idComida'];
    });

    // Capturamos la fecha desde los queryParams para volver a esa misma fecha después
    this.route.queryParams.subscribe(query => {
      this.fechaSeleccionada = query['fecha'] || '';
    });
  }

  // Buscar alimentos con texto
  onBuscar(texto: string): void {
    this.alimentosService.getAlimentos(texto).subscribe({
      next: (res) => this.alimentos = res,
      error: () => this.alimentos = []
    });
  }

  // Guardamos el alimento elegido
  seleccionarAlimento(alimento: Alimento): void {
    this.alimentoSeleccionado = alimento;
  }

  // Añadimos el alimento a la comida
  agregarAlimento(): void {
    if (!this.alimentoSeleccionado || this.porciones <= 0) return;

    const request = {
      idAlimento: this.alimentoSeleccionado.idAlimento,
      cantidad: this.porciones
    };

    this.comidaService.agregarAlimentoAComida(this.idComida, request).subscribe({
      // Cuando se añade correctamente, volvemos al diario del día seleccionado
      next: () => {
        this.router.navigate(['/usuario/diario'], {
          queryParams: { fecha: this.fechaSeleccionada }
        });
      },
      error: () => {}
    });
  }
}