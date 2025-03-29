package easyfit.models.dtos;

import easyfit.models.enums.Actividad;
import easyfit.models.enums.AjustePeso;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjetivoRequestDto {

	@NotNull(message = "Debes seleccionar un peso actual")
    private double pesoActual;
	
	@NotNull(message = "Debes seleccionar un peso objetivo")
    private double pesoObjetivo;

    @NotNull(message = "Debes seleccionar un ajuste de peso")
    private AjustePeso opcionPeso;

    @NotNull(message = "Debes seleccionar un nivel de actividad")
    private Actividad actividad;
}
