import { Objetivo } from "./objetivo";
import { Usuario } from "./usuario";

export interface Registro {

    usuario: Usuario,
    objetivo: Objetivo,
    token?: string
}
