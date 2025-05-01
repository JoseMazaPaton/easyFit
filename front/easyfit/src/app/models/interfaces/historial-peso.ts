export interface HistorialPeso {
    pesoInicial: number;
    pesoActual: number;
    pesoObjetivo: number;
    diferenciaKg: number;
    porcentajeProgreso: number;
    objetivoUsuario: 'PERDERPESO' | 'MANTENER' | 'GANARPESO'; 
}
