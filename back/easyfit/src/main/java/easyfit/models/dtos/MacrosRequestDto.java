package easyfit.models.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MacrosRequestDto {

	//POR AQUI LLEGAN LOS MACROS EN PORCENTAJE, NO EN GRAMOS
	
	
    @NotNull(message = "El porcentaje de proteínas no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El porcentaje de proteínas no puede ser negativo")
    @DecimalMax(value = "100.0", message = "El porcentaje de proteínas no puede superar 100")
    private double porcentajeProteinas;

    @NotNull(message = "El porcentaje de carbohidratos no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El porcentaje de carbohidratos no puede ser negativo")
    @DecimalMax(value = "100.0", message = "El porcentaje de carbohidratos no puede superar 100")
    private double porcentajeCarbohidratos;

    @NotNull(message = "El porcentaje de grasas no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El porcentaje de grasas no puede ser negativo")
    @DecimalMax(value = "100.0", message = "El porcentaje de grasas no puede superar 100")
    private double porcentajeGrasas;
    
    //RESTRICCION EN LA VALIDACIONES PERSONALIZA
    //La suma de los 3 porcentajes tiene que ser exactamente el 100%
    @AssertTrue(message = "La suma de los porcentajes tiene que ser del 100%")
    public boolean isSumaPorcentajesValida() {
        double suma = porcentajeProteinas + porcentajeCarbohidratos + porcentajeGrasas;
        return Math.abs(suma - 100.0) < 0.01; // tolerancia para evitar errores de redondeo
    }

}
