package easyfit.models.dtos.auth;

import easyfit.models.enums.Sexo;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequestDto {

    @NotBlank
    private String nombre;

    @NotBlank
    @Email(message = "El formato del email no es válido.")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotNull
    private Sexo sexo;

    @Min(10)
    private int edad;

    @Positive
    private double altura;
}
