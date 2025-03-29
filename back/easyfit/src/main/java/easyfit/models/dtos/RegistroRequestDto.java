package easyfit.models.dtos;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private UsuarioRequestDto usuario;
    
    //DATOS DE LOS OBJETIVOS
    @NotNull
    private ObjetivoRequestDto objetivo;
}
