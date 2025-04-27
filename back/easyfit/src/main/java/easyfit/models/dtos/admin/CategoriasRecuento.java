package easyfit.models.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriasRecuento {

    private int idCategoria;
    
    private String nombre;
    
    private int totalAlimentos;
    
    public CategoriasRecuento(int idCategoria, String nombre, long totalAlimentos) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.totalAlimentos = (int) totalAlimentos;
    }
}
