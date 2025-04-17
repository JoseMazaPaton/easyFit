import { IUsuarioPorMes } from "./IUsuarioPorMes";

export interface IUsuarioResumen {
    registradosEsteMes: number;
    registradosMesAnterior: number;
    mediaRegistros6Meses: number;
    totalUsuariosActivos: number;
    totalHombres: number;
    totalMujeres: number;
    registrosPorMes: IUsuarioPorMes[];
  }