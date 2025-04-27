package easyfit.models.dtos.admin;



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
public class UsuarioAdminListaDto {

    private String nombre;
    
    private String email;
    
    private String password;
    
    private Sexo sexo;
    
    private int edad;
    
    private double altura;
    
    private boolean suspendida;
    
    private LocalDate fechaRegistro;
}
