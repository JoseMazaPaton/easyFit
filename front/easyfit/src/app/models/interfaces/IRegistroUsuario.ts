export interface IRegistroUsuario {
    // Datos del usuario
    usuario: Partial<{
      nombre: string;
      email: string;
      password: string;
      sexo: 'HOMBRE' | 'MUJER';
      edad: number;
      altura: number;
    }>;
  
  
    // Datos del objetivo del usuario registrado
    objetivo: Partial<{
      pesoActual: number;
      pesoObjetivo: number;
      objetivoUsuario: 'PERDERPESO' | 'MANTENER' | 'GANARPESO';
      actividad: 'SEDENTARIO' | 'LIGERO' | 'MODERADO' | 'ACTIVO' | 'MUYACTIVO';
      opcionPeso?: string;
    }>;
  }
  