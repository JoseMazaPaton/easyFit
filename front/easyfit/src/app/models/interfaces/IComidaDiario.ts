import { Alimento } from './alimento';

export interface IComidaDiariaDto {

  idComida: number;
  nombre: string;
  orden: number;
  fecha: Date;
  alimentos: Alimento [];
}