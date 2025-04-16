import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegistroService } from '../../../models/services/registro.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-paso1-bienvenida',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './paso1-bienvenida.component.html',
  styleUrl: './paso1-bienvenida.component.css'
})
export class Paso1BienvenidaComponent {

  
  formPaso1!: FormGroup;

  constructor(
    private fb: FormBuilder, 
    private registroService: RegistroService, 
    private router: Router) {}

  ngOnInit() {
    this.formPaso1 = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(4)]],
      email: ['', [Validators.required, Validators.email]]
    });
  }
  
  siguientePaso() {
    if (this.formPaso1.valid) {
      const { nombre, email } = this.formPaso1.value;
      this.registroService.setPaso({ usuario: { nombre, email } });
      this.router.navigate(['/auth/register/paso2']);
    }
  }
  
  checkControl(controlName: string, error: string): boolean {
    const control = this.formPaso1.get(controlName);
    return !!control && control.hasError(error) && (control.dirty || control.touched);
  }
  
  
}
