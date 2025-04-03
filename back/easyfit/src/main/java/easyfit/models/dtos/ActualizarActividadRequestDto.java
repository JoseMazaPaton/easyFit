package easyfit.models.dtos;

import easyfit.models.enums.Actividad;
import lombok.Data;

@Data
public class ActualizarActividadRequestDto {
	private Actividad actividad;
}
