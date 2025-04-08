import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../models/services/auth.service';


@Component({
  standalone: true,
  selector: 'app-register-form',
  imports: [ReactiveFormsModule],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css',
})
export class RegisterFormComponent {

  registroForm: FormGroup;
  step: number;

  constructor (private http: HttpClient,
                private authService: AuthService
  ) {
    this.step = 1;

    this.registroForm = new FormGroup({
      email: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      nombre: new FormControl('', [Validators.required]),
      edad: new FormControl('', [Validators.required]),
      sexo: new FormControl('', [Validators.required]),
      altura: new FormControl('', [Validators.required]),
      pesoActual: new FormControl('', [Validators.required]),
      pesoObjetivo: new FormControl('', [Validators.required]),
      objetivoUsuario: new FormControl('', [Validators.required]),
      opcionPeso: new FormControl('', [Validators.required]),
      actividad: new FormControl('', [Validators.required])
    },
    [])
  }

  registrarUsuario() {

  }

  checkControl(formControlName: string, validador: string): boolean | undefined{
    return this.registroForm.get(formControlName)?.hasError(validador) && this.registroForm.get(formControlName)?.touched; 
  }
}
