package easyfit.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KcalYMacrosDto {

    private int kcalObjetivo;
    
    private double proteinas;
    
    private double carbohidratos;
    
    private double grasas;
}
