package easyfit.models.dtos.objetivo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PesoObjetivoDto {
	
	@NotNull(message = "El peso actual no puede ir vacio")
	@Positive(message = "El peso nunca puede ser negativo")
	 private double pesoObjetivo;
}
