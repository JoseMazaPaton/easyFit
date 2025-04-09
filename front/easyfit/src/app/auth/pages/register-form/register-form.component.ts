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
  /*
  register(datos: any) {
    return this.http.post('tu-api-url/registro', datos);
  }*/
   

  registrarUsuario() {
    /*
    // Primero, comprobamos que el formulario es válido
    if (this.registroForm.invalid) {
      // Puedes marcar todos los controles como touched para que se muestren los mensajes de error
      this.registroForm.markAllAsTouched();
      return;
    }
    // Extraemos los datos del formulario
    const datosRegistro = this.registroForm.value;
    // Llamamos al método del servicio para registrar el usuario.
    // El método register debe devolver un observable con la respuesta del servidor.
    this.authService.register(datosRegistro).subscribe(
      (response: any) => {
        console.log('Usuario registrado correctamente', response);
        // Aquí puedes redirigir o mostrar un mensaje de éxito
      },
      (error: any) => {
        console.error('Error en el registro', error);
        // Maneja los errores, por ejemplo mostrando un mensaje al usuario
      }
    );
    */
  }

  checkControl(formControlName: string, validador: string): boolean | undefined{
    return this.registroForm.get(formControlName)?.hasError(validador) && this.registroForm.get(formControlName)?.touched; 
  }
}
