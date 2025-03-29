package easyfit.models.dtos;

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
    @Email
    private String email;

    @NotBlank
    @Size(min = 10)
    private String password;

    @NotNull
    private Sexo sexo;

    @Min(10)
    private int edad;

    @Positive
    private double altura;
}
