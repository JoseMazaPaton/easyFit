package easyfit.models.entities;

import java.io.Serializable;

import easyfit.models.enums.Actividad;
import easyfit.models.enums.AjustePeso;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of="idObjetivo")
@Entity
@Table(name="objetivos")
public class Objetivo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_objetivo")
	private int idObjetivo;
	
	@Column(name = "peso_actual")
	private double pesoActual;
	
	@Column(name = "peso_objetivo")
	private double pesoObjetivo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "opcion_peso", nullable = false)
	private AjustePeso opcionPeso;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "actividad", nullable = false)
	private Actividad actividad;
	
	@Column(name = "kcal_objetivo")
	private int kcalObjetivo;
	
	private double proteinas;
	private double carbohidratos;
	private double grasas;

	// ANOTACIONES RELACIONES DE OBJETIVO ===========================================================================

	// Esta relacion es la parte "dueña" entre Objetivo y Usuario
	// La FK se gestiona en esta tabla en la bbdd
	// Aunque un usuario solo tiene un objetivo activo, en la base de datos se pueden guardar varios con el tiempo
	// Por eso usamos esta relación, para enlazar el objetivo activo con el usuario correspondiente y que no haya problemas
	@ManyToOne
	@JoinColumn(name = "email")
	private Usuario usuario;
}
