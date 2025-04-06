package easyfit.models.dtos.objetivo;

import easyfit.models.enums.Actividad;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelActividadDto {
	
    @NotNull(message = "Debes seleccionar un nivel de actividad")
    private Actividad actividad;
    
}
