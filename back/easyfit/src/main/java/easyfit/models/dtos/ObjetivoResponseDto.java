package easyfit.models.dtos;

import easyfit.models.enums.Actividad;
import easyfit.models.enums.AjustePeso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjetivoResponseDto {

	//Objetivos modificables por el usuario
    private double pesoActual;
    private double pesoObjetivo;
    private AjustePeso opcionPeso;
    private Actividad actividad;

    //Datos calculados 
    private int kcalObjetivo;
    private double proteinas;
    private double carbohidratos;
    private double grasas;
}
