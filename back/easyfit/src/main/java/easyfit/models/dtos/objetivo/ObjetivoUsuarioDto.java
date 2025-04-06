package easyfit.models.dtos.objetivo;

import easyfit.models.enums.ObjetivoUsuario;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjetivoUsuarioDto 
{
	@NotNull(message = "Debes seleccionar un objetivo")
	private ObjetivoUsuario objetivoUsuario;
}
