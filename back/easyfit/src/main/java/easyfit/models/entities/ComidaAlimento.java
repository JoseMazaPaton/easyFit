package easyfit.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class ComidaAlimento implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comida_alimento")
    private int idComidaAlimento;

    private double cantidad;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_comida")
    private Comida comida;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_alimento")
    private Alimento alimento;


}
