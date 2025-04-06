package easyfit.models.dtos.objetivo;

import easyfit.models.dtos.valornutricional.ValorNutriconalResponseDto;
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
    
    private boolean coherente;

    //Datos de calorias y macros
    private ValorNutriconalResponseDto valores;
    

}
