import { Objetivo } from "./objetivo";
import { Usuario } from "./usuario";

export interface RegisterResponse {

    usuario: {
        id: number,
        nombre: string,
        email: string,
        edad: number,
        sexo: string,
        altura: number
    },
    objetivo: {
        pesoActual: number
        pesoObjetivo: number
        objetivoUsuario: string
        opcionPeso?: string
        actividad: string
    },
    token: string
}
