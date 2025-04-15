import { ValorNutricional } from "./valor-nutricional";

export interface ObjetivoResponse {
    pesoActual: number;
    pesoObjetivo: number;
    actividad: string;
    opcionPeso: string;
    objetivoUsuario: string;
    coherente: boolean;
    valores: ValorNutricional;
  }