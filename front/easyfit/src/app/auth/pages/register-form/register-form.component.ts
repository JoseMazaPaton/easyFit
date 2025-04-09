import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../models/services/auth.service';
import { RegisterUser } from '../../../models/interfaces/registerUser';
import { Usuario } from '../../../models/interfaces/usuario';
import { Objetivo } from '../../../models/interfaces/objetivo';
import { Router } from '@angular/router';


@Component({
  standalone: true,
  selector: 'app-register-form',
  imports: [ReactiveFormsModule],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css',
})
export class RegisterFormComponent {

  registroForm: FormGroup;
  step: number = 1;

  constructor (private http: HttpClient,
              private authService: AuthService,
              private router: Router
  ) {
  
    this.registroForm = new FormGroup({
      email: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required]),
      nombre: new FormControl(null, [Validators.required]),
      edad: new FormControl(null, [Validators.required]),
      sexo: new FormControl(null, [Validators.required]),
      altura: new FormControl(null, [Validators.required]),
      pesoActual: new FormControl(null, [Validators.required]),
      pesoObjetivo: new FormControl(null, [Validators.required]),
      objetivoUsuario: new FormControl(null, [Validators.required]),
      opcionPeso: new FormControl(null, [Validators.required]),
      actividad: new FormControl(null, [Validators.required])
    },
    [])
  }

  registrarUsuario() {
    if (this.registroForm.invalid) {
      // Marcamos todos los controles como "touched" para mostrar errores si existen
      this.registroForm.markAllAsTouched();
      return;
    }

    const usuario: Usuario = {
      email: this.registroForm.get('email')?.value,
      password: this.registroForm.get('password')?.value,
      nombre: this.registroForm.get('nombre')?.value,
      edad: this.registroForm.get('edad')?.value,
      sexo: this.registroForm.get('sexo')?.value,
      altura: this.registroForm.get('altura')?.value
    }

    const objetivo: Objetivo = {
      pesoActual: this.registroForm.get('pesoActual')?.value,
      pesoObjetivo: this.registroForm.get('pesoObjetivo')?.value,
      objetivoUsuario: this.registroForm.get('objetivoUsuario')?.value,
      opcionPeso: this.registroForm.get('opcionPeso')?.value,
      actividad: this.registroForm.get('actividad')?.value
    }

    const usuarioReg: RegisterUser = {
      usuario,
      objetivo
    }
    console.log(usuario)
    // Llamamos al método del servicio para registrar al usuario
    this.authService.register(usuarioReg).subscribe({
      next: (response: any) => {
        console.log('Usuario registrado correctamente', response);
        alert('Usuario registrado correctamente');
        // Aquí podrías redirigir al usuario, limpiar el formulario o mostrar un mensaje de éxito
        this.router.navigate(['/usuario'])
      },
      error: (error: any) => {
        console.error('Error en el registro', error);
        alert('Error en el registro, por favor intenta nuevamente');  
        // Maneja el error, por ejemplo mostrando un mensaje al usuario
      }
    });
  }
  

  // checkControl(formControlName: string, validador: string): boolean | undefined{
  //   return this.registroForm.get(formControlName)?.hasError(validador) && this.registroForm.get(formControlName)?.touched; 
  // }

  checkControl(formControlName: string, validador: string): boolean {
    const control = this.registroForm.get(formControlName);
    const camposPaso1 = ['email', 'password', 'nombre', 'edad', 'sexo', 'altura'];
    const camposPaso2 = ['pesoActual', 'pesoObjetivo', 'objetivoUsuario', 'opcionPeso', 'actividad'];
  
    const camposVisibles = this.step === 1 ? camposPaso1 : camposPaso2;
  
    return camposVisibles.includes(formControlName) &&
           !!(control && control.hasError(validador) && (control.touched || control.dirty));
  }
  

  

  siguientePaso() {
    const camposPaso1 = ['email', 'password', 'nombre', 'edad', 'sexo', 'altura'];
    let pasoValido = true;
  
    camposPaso1.forEach(campo => {
      const control = this.registroForm.get(campo);
      control?.markAsTouched();  // Marca el campo como tocado
      if (!control?.valid) {
        pasoValido = false;
      }
    });
  
    if (pasoValido) {
      this.step = 2;
  
      // Reinicia el estado de los campos del paso 2 (sin errores visibles)
      const camposPaso2 = ['pesoActual', 'pesoObjetivo', 'objetivoUsuario', 'opcionPeso', 'actividad'];
      camposPaso2.forEach(campo => {
        const control = this.registroForm.get(campo);
        control?.markAsUntouched();  // Reinicia el estado de tocado
        control?.markAsPristine();   // Reinicia el estado como si nunca se hubiera tocado
      });
    }
  }

  

  isPaso1Valido(): boolean {
    const camposPaso1 = ['email', 'password', 'nombre', 'edad', 'sexo', 'altura'];
    return camposPaso1.every(campo => {
      const control = this.registroForm.get(campo);
      return control?.valid;
    });
  }


  anteriorPaso() {
    this.step = 1;
  }
  
}
