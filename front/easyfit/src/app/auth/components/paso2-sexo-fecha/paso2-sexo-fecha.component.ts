import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegistroService } from '../../../models/services/registro.service';

@Component({
  selector: 'app-paso2-sexo-fecha',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './paso2-sexo-fecha.component.html',
  styleUrl: './paso2-sexo-fecha.component.css'
})
export class Paso2SexoFechaComponent {

  formPaso2!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private registroService: RegistroService,
    private router: Router
  ) {}

  ngOnInit() {
    this.formPaso2 = this.fb.group({
      sexo: ['', Validators.required],
      edad: [null, [Validators.required, Validators.min(12), Validators.max(100)]]
    });
  }

  siguientePaso() {
    if (this.formPaso2.invalid) {
      this.formPaso2.markAllAsTouched();
      return;
    }
  
    const { sexo, edad } = this.formPaso2.value;
  
    this.registroService.setPaso({
      usuario: { sexo, edad }
    });
  
    this.router.navigate(['/auth/register/paso3']);
  }
  
  checkControl(controlName: string, error: string): boolean {
    const control = this.formPaso2.get(controlName);
    return !!control && control.hasError(error) && control.touched;
  }
}