package easyfit.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of="idProgreso")
@Entity
@Table(name="progresos")
@Schema(description = "Entidad que representa el Progreso del Usuario")
public class Progreso implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_progreso")
	@Schema(description = "Nº ID del Progreso de Usuario", example = "1")
	private int idProgreso;
	
	// Entidad corregida
	@Schema(description = "Peso del Usuario", example = "70.8(kilogramos)")
	@Column(name = "peso")
	private double peso;

	@Schema(description = "Fecha del Cambio del Progreso del Usuario", example = "2025-05-10")
	@Column(name = "fecha_cambio")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime fechaCambio;
	
	@Schema(description = "Relación Del Progreso con Usuario", example = "jose@gmail.com")
	@ManyToOne
	@JoinColumn(name = "email")
	private Usuario usuario;
	
	
}
