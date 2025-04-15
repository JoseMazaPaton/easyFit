import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Alimento } from '../../../../models/interfaces/alimento';
import { Categoria } from '../../../../models/interfaces/categoria';
import { AlimentosService } from '../../../../models/services/alimentos.service';

@Component({
  selector: 'app-alimentos-formulario',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './alimentos-formulario.component.html',
  styleUrl: './alimentos-formulario.component.css'
})
export class AlimentosFormularioComponent {

  @Input() alimento: Alimento | null = null;
  @Input() categorias: Categoria[] = [];
  @Output() guardado = new EventEmitter<Alimento>();
  @Output() cerrar = new EventEmitter<void>();
  @Output() eliminarAlimento = new EventEmitter<Alimento>();

  form!: FormGroup;

  constructor(private fb: FormBuilder,
              private alimentosService: AlimentosService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: [this.alimento?.nombre || '', Validators.required],
      marca: [this.alimento?.marca || ''],
      kcal: [this.alimento?.kcal || 0, [Validators.required, Validators.min(0)]],
      proteinas: [this.alimento?.proteinas || 0, [Validators.min(0)]],
      carbohidratos: [this.alimento?.carbohidratos || 0, [Validators.min(0)]],
      grasas: [this.alimento?.grasas || 0, [Validators.min(0)]],
      unidadMedida: [this.alimento?.unidadMedida || 'g', Validators.required],
      idCategoria: [this.alimento?.idCategoria || null, Validators.required]
    });
  }

  onSubmit() {
    if (this.form.invalid) return;
  
    const dto: Alimento = {
      ...this.alimento,
      ...this.form.value
    };
  
    const request = this.alimento
      ? this.alimentosService.actualizarAlimento(this.alimento.idAlimento!, dto)
      : this.alimentosService.crearAlimento(dto);
  
    request.subscribe({
      next: () => {
        this.guardado.emit();       // ✅ avisa al padre que guarde/recargue
        this.form.reset();          // 🧼 limpia el formulario
      },
      error: (e) => {
        console.error('❌ Error al guardar alimento:', e);
      }
    });
  }

  cancelar() {
    this.cerrar.emit();
  }

  onEliminar(alimento: Alimento): void {
    if (confirm(`¿Estás seguro de eliminar el alimento "${alimento.nombre}"?`)) {
      this.eliminarAlimento.emit(alimento);
    }
  }
}
