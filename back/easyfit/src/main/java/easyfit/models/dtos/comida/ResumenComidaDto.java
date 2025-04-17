// ResumenComidaDto.java
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
public class ResumenComidaDto {
	
    private String nombreComida;
    private LocalDate fecha;
    private int totalKcal;
    private double totalProteinas;
    private double totalCarbohidratos;
    private double totalGrasas;
    private List<AlimentoEnComidaDto> alimentos;
}
