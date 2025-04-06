package easyfit.models.dtos.objetivo;

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
public class AjusteSemanalDto {
	
    @NotNull(message = "Debes seleccionar un ajuste de peso")
    private OpcionPeso opcionPeso;
}
