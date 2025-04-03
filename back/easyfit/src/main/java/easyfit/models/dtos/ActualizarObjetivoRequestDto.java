package easyfit.models.dtos;

import easyfit.models.enums.ObjetivoUsuario;
import lombok.Data;

@Data
public class ActualizarObjetivoRequestDto {
	private ObjetivoUsuario objetivoUsuario;
}
