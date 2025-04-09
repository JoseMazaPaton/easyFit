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

	private int kcalConsumidas;
	
	private int KcalObjetivo;

	private int KcalRestantes;
	

	public int getKcalRestantes() {
		return KcalObjetivo - kcalConsumidas;
	}
}
