package easyfit.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "idConsumo")
@Entity
@Table(name = "consumo_diario")
@Schema(description = "Entidad que representa el Consumo Diario de Alimento por el Usuario")
public class ConsumoDiario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consumo")
	@Schema(description = "Nº de ID del Consumo", example = "1")
    private int idConsumo;
    
	@Schema(description = "Fecha del día del Consumo de Alimento", example = "2025-04-16")
    @Column(nullable = false)
    private LocalDate fecha;

	@Schema(description = "Calorías consumidas por el Usuario", example = "600")
    @Column(name = "kcal_consumidas", nullable = false)
    private int kcalConsumidas;
    
	@Schema(description = "Proteínas consumidas por el Usuario", example = "110.5")
    private double proteinas;
	
	@Schema(description = "Carbohidratos consumidos por el Usuario", example = "220.8")
    private double carbohidratos;
	
	@Schema(description = "Grasas consumidas por el Usuario", example = "70.0")
    private double grasas;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
	@Schema(description = "Columna que hace referencia al Usuario", example = "frank@gmail.com")
    private Usuario usuario;
}
