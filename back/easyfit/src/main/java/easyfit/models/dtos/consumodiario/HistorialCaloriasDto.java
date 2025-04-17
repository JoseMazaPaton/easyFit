package easyfit.models.dtos.consumodiario;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialCaloriasDto {
	
    private LocalDate fecha;     
    private int kcalObjetivo;   
    private int kcalConsumidas;   
}


