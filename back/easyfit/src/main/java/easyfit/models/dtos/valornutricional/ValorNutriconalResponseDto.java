package easyfit.models.dtos.valornutricional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValorNutriconalResponseDto {

    private int kcalObjetivo;
    
    // Datos en gramos
    private double proteinas;
    private double carbohidratos;
    private double grasas;
    
    // Datos en porcentajes
    private double porcentajeProteinas;
    private double porcentajeCarbohidratos;
    private double porcentajeGrasas;
}
