package easyfit.models.dtos.admin;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UserResumenDto {
    private int registradosEsteMes;
    private int registradosMesAnterior;
    private double mediaRegistros6Meses;
    private int totalUsuariosActivos;
    private int totalHombres;
    private int totalMujeres;
    private List<UsuarioPorMesDto> registrosPorMes;
}
