package easyfit.models.dtos.alimentos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimentoDto {
	
	private Integer idAlimento;
    private String nombre;
    private String marca;
    private String unidadMedida;
    private int kcal;
    private double proteinas;
    private double carbohidratos;
    private double grasas;
    private int idCategoria;
}