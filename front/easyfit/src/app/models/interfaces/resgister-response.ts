import { Objetivo } from "./objetivo";
import { Usuario } from "./usuario";

export interface ResgisterResponse {

    usuario: Usuario,
    objetivo: Objetivo,
    token: string
}
