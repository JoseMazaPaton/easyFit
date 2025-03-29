package easyfit.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroResponseDto {

	//DATOS DEL USUARIO
    private UsuarioResponseDto usuario;
    
    //DATOS DE LOS OBJETIVOS
    private ObjetivoResponseDto objetivo;
}
