package easyfit.models.dtos.auth;

import easyfit.models.dtos.objetivo.ObjetivoRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroRequestDto {
	
	//DATOS DEL USUARIO
	@Valid // Esto es para que se validen las validaciones que llevan anidadas 
	//*NOTA : Dentro de usuario NO hay ROL porque ya lo hemos puesto por defecto en la entidad*
    private UsuarioRequestDto usuario;

    
   //DATOS OBJETIVO
    @Valid
	private ObjetivoRequestDto objetivo;
    
    
}
