// ComidaDiariaDto.java
package easyfit.models.dtos.comida;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import easyfit.models.dtos.alimentos.AlimentoEnComidaDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComidaDiariaDto {
	
    private int idComida;
    
    private String nombre;
    
    private LocalDate fecha;
    
    private int orden;
    
    private List<AlimentoEnComidaDto> alimentos;
}