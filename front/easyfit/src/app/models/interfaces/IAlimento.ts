export interface IAlimento {
    idAlimento: number;
    nombre: string;
    marca: string;
    kcal: number;
    categoria: {
      id: number;
      nombre: string;
    };
  }