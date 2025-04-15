import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../models/services/auth.service';
import { RegisterUser } from '../../../models/interfaces/registerUser';
import { Usuario } from '../../../models/interfaces/usuario';
import { Objetivo } from '../../../models/interfaces/objetivo';
import { Router, RouterOutlet } from '@angular/router';


@Component({
  standalone: true,
  selector: 'app-register-form',
  imports: [RouterOutlet],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css',
})
export class RegisterFormComponent {
}