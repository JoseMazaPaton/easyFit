package easyfit.models.dtos.consumodiario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialPesoDto {
	
	//Datos de los pesos
    private double pesoInicial;
    private double pesoActual;
    private double pesoObjetivo;

    //Resultados
    private double diferenciaKg;

    private int porcentajeProgreso;

}
