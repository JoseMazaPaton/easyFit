package easyfit.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Progreso implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_progreso")
	private int idProgreso;
	
	// Entidad corregida
	@Column(name = "peso")
	private double peso;

	@Column(name = "fecha_cambio")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime fechaCambio;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Usuario usuario;
	
	
}
