export interface ObjetivoResponse {
    pesoActual: number;
    pesoObjetivo: number;
    actividad: string;
    opcionPeso: string;
    objetivoUsuario: string;
    coherente: boolean;
    valores: {
      kcalObjetivo: number;
      proteinas: number;
      carbohidratos: number;
      grasas: number;
      porcentajeProteinas: number;
      porcentajeCarbohidratos: number;
      porcentajeGrasas: number;
    };
  }