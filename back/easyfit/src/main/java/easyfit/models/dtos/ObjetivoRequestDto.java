package easyfit.models.dtos;

import easyfit.models.enums.Actividad;
import easyfit.models.enums.ObjetivoUsuario;
import easyfit.models.enums.OpcionPeso;
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

	@NotNull(message = "Debes seleccionar un objetivo")
	private ObjetivoUsuario objetivoUsuario;
	
	@NotNull(message = "Debes seleccionar un peso actual")
    private double pesoActual;
	
	@NotNull(message = "Debes seleccionar un peso objetivo")
    private double pesoObjetivo;

    @NotNull(message = "Debes seleccionar un ajuste de peso")
    private OpcionPeso opcionPeso;

    @NotNull(message = "Debes seleccionar un nivel de actividad")
    private Actividad actividad;
}
