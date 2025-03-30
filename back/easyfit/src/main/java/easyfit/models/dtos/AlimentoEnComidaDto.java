package easyfit.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimentoEnComidaDto {
	 private int idAlimento;
    private String nombre;
    private String marca;
    private double cantidad;
    private String unidadMedida;
    private int kcal;
    private double proteinas;
    private double carbohidratos;
    private double grasas;
}
