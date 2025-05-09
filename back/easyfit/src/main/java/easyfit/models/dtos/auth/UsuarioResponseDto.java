package easyfit.models.dtos.auth;

import java.time.LocalDate;

import easyfit.models.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDto {

    private String nombre;
    
    private String email;
    
    private String password;
    
    private Sexo sexo;
    
    private int edad;
    
    private double altura;
    
    private LocalDate fechaRegistro;
}
