package easyfit.models.dtos;

import easyfit.models.enums.OpcionPeso;
import lombok.Data;

@Data
public class ActualizarPesoRequestDto {
	 private double campoPeso;
	 private OpcionPeso opcionPeso;
}
