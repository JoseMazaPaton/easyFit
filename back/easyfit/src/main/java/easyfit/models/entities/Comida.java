package easyfit.models.entities;
import java.io.Serializable;
import java.time.LocalDate;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of="idComida")
@Entity
@Table(name="comidas")
@Schema(description = "Entidad que representa una Comida compuesta por Alimentos")
public class Comida implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comida")
	@Schema(description = "Nº de ID de la Comida", example = "1")
	private int idComida;
	
	@Schema(description = "Nombre de la comida", example = "Paella")
	private String nombre;
	
	@Schema(description = "Nº de orden de la comida. (1 = Desayuno, 2 = Almuerzo, 3 = Comida, etc...)", example = "1 (Desayuno)")
	private int orden;
	
	@Schema(description = "Fecha de la comida", example = "2025-04-16")
	private LocalDate fecha;
	
	@ManyToOne
	@JoinColumn(name = "email")
	@Schema(description = "Usuario que tiene registrada la Comida", example = "frank@gmail.com")
	private Usuario usuario;

	
}
