export interface IUsuario {

    nombre: string;
    password?: string; // opcional si no lo quieres mostrar
    email: string;
    sexo: string;
    edad: number;
    altura: number;
    fechaRegistro: string;
    suspendida: boolean;

}
