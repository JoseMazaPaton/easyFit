package easyfit.models.dtos;

import easyfit.models.enums.Actividad;
import easyfit.models.enums.ObjetivoUsuario;
import easyfit.models.enums.OpcionPeso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjetivoResponseDto {

    private ObjetivoUsuario objetivoUsuario;
    
    private double pesoActual;
    
    private double pesoObjetivo;
    
    private OpcionPeso opcionPeso;
    
    private Actividad actividad;

    //Datos de calorias y macros
    private ValorNutriconalResponseDto valores;
    

}
