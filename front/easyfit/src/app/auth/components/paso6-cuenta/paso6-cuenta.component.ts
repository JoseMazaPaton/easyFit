import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ValidatorFn, AbstractControl, ValidationErrors, ReactiveFormsModule } from '@angular/forms';
import { Router} from '@angular/router';
import { RegistroService } from '../../../models/services/registro.service';
import { AuthService } from '../../../models/services/auth.service';

@Component({
  selector: 'app-paso6-cuenta',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './paso6-cuenta.component.html',
  styleUrl: './paso6-cuenta.component.css'
})
export class Paso6CuentaComponent {
  formPaso6!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private registroService: RegistroService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.formPaso6 = this.fb.group(
      {
        password: ['', [Validators.required, Validators.minLength(10), this.noEspaciosValidator()]],
        confirmarPassword: ['', [Validators.required]]
      },
      { validators: this.passwordsIguales() }
    );
  }

  checkControl(controlName: string, error: string): boolean {
    const control = this.formPaso6.get(controlName);
    return !!control && control.hasError(error) && control.touched;
  }

  noEspaciosValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null =>
      control.value?.includes(' ') ? { noEspacios: true } : null;
  }

  passwordsIguales(): ValidatorFn {
    return (group: AbstractControl): ValidationErrors | null => {
      const password = group.get('password')?.value;
      const confirm = group.get('confirmarPassword')?.value;
      return password === confirm ? null : { noCoincide: true };
    };
  }

  crearCuenta() {
    if (this.formPaso6.invalid) {
      this.formPaso6.markAllAsTouched();
      return;
    }

    const password = this.formPaso6.value.password;
    const registroFinal = this.registroService.getDatos();
    registroFinal.usuario = {
      ...registroFinal.usuario,
      password
    };

    // 💥 JSON para comprobar
    console.log('%cREGISTRO FINAL:', 'color: #0099ff; font-weight: bold;');
    console.log(JSON.stringify(registroFinal, null, 2));

    // ✅ Enviar al backend
    this.authService.registrarUsuario(registroFinal).subscribe({
      next: (res) => {
        console.log('✅ Registro exitoso:', res);
        this.router.navigate(['/auth/login']);
      },
      error: (err) => {
        console.error('❌ Error al registrar:', err);
        alert('Error al crear la cuenta. Inténtalo de nuevo.');
      }
    });
  }
}