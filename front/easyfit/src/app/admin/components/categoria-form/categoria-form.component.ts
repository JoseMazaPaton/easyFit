import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Categoria } from '../../../models/interfaces/categoria';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-categoria-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './categoria-form.component.html',
  styleUrl: './categoria-form.component.css'
})
export class CategoriaFormComponent {
  @Input() categoria?: Categoria;
  @Output() cerrar = new EventEmitter<void>();
  @Output() eliminarCategoria = new EventEmitter<Categoria>();
  @Output() guardado = new EventEmitter<Categoria>();

  formCategoria: FormGroup;

  constructor(private fb: FormBuilder) {
    this.formCategoria = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(50)]]
    });
  }

  ngOnInit() {
    if (this.categoria) {
      console.log('✏️ Editando categoría:', this.categoria);
      this.formCategoria.patchValue({
        nombre: this.categoria.nombre
      });
    } else {
      console.log('➕ Creando nueva categoría');
    }
  }

  guardarCategoria() {
    if (this.formCategoria.valid) {
      const categoriaGuardada: Categoria = {
        idCategoria: this.categoria?.idCategoria ?? 0,
        nombre: this.formCategoria.value.nombre,
        totalAlimentos: this.categoria?.totalAlimentos ?? 0
      };
      console.log('✅ Guardando categoría:', categoriaGuardada);
      this.guardado.emit(categoriaGuardada);
      this.cerrar.emit();
    } else {
      console.warn('⚠️ Formulario no válido. Corrige los errores.');
    }
  }

  eliminar() {
    if (this.categoria) {
      console.log('🗑️ Eliminando categoría:', this.categoria);
      this.eliminarCategoria.emit(this.categoria);
      this.cerrar.emit();
    }
  }
}