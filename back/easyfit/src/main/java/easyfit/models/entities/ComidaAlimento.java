package easyfit.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="proyecto_con_empleados")
public class ComidaAlimento {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_comida")
    private Comida comida;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_alimento")
    private Alimento alimento;

    private double cantidad;
}
