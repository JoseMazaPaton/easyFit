// AgregarAlimentoRequest.java
package easyfit.models.dtos.alimentos;

import lombok.Data;

@Data
public class AgregarAlimentoRequestDto {
    private int idAlimento;
    private double cantidad;
}
