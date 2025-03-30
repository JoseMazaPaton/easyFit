// ComidaDiariaDto.java
package easyfit.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComidaDiariaDto {
    private int idComida;
    private String nombre;
    private int orden;
    private List<AlimentoEnComidaDto> alimentos;
}