package easyfit.models.entities;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name="comidas_alimentos")
@Schema(description = "Entidad que representa la relación de las Comidas y los Alimentos")
public class ComidaAlimento implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comida_alimento")
	@Schema(description = "Nº de ID de la Comida Alimento", example = "1")
    private int idComidaAlimento;

	@Schema(description = "Cantidad de Comida", example = "70.8(gramos)")
    private double cantidad;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_comida")
	@Schema(description = "Nº de ID de la Comida", example = "1")
    private Comida comida;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_alimento")
	@Schema(description = "Nº de ID de la  Alimento", example = "1")
    private Alimento alimento;

}
