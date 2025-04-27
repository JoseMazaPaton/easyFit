package easyfit.models.dtos.consumodiario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiarioResponseDto {

	private double kcalConsumidas;
	
	private double KcalObjetivo;

	private double KcalRestantes;
	

	public double getKcalRestantes() {
		return KcalObjetivo - kcalConsumidas;
	}
}
