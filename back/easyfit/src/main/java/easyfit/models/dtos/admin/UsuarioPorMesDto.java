package easyfit.models.dtos.admin;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioPorMesDto {
    private String mes;
    private int hombres;
    private int mujeres;
}
