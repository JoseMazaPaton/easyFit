import { Injectable } from '@angular/core';
import { IRegistroUsuario } from '../interfaces/IRegistroUsuario';

@Injectable({ providedIn: 'root' })
export class RegistroService {


    private usuarioRegistro: Partial<IRegistroUsuario> = {};
  
    // Guarda y acumula los datos de cada paso sin sobrescribir el resto
    setPaso(parte: Partial<IRegistroUsuario>) {
      this.usuarioRegistro = {
        ...this.usuarioRegistro,
        usuario: {
          ...this.usuarioRegistro.usuario,
          ...parte.usuario
        },
        objetivo: {
          ...this.usuarioRegistro.objetivo,
          ...parte.objetivo
        }
      };
    }
  
    // Devuelve todos los datos del registro
    getDatos(): IRegistroUsuario {
      return this.usuarioRegistro as IRegistroUsuario;
    }
  
    // Reinicia todo el proceso de registro
    reset() {
      this.usuarioRegistro = {};
    }
  }