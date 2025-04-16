package easyfit.models.dtos.auth;

import easyfit.models.dtos.objetivo.ObjetivoResponseDto;
import easyfit.models.dtos.valornutricional.ValorNutriconalResponseDto;
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
    
    //DATOS DE LOS VALORES DEL USURIO
    private ValorNutriconalResponseDto valores;
    
    //TOKEN
    private String token;
}
