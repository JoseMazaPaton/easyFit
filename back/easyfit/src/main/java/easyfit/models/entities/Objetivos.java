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
public class Objetivos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_objetivo")
	private int idObjetivo;
	
	@Column(name = "peso_actual")
	private double pesoActual;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ajuste_peso", nullable = false)
	private AjustePeso opcionPeso;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "actividad", nullable = false)
	private Actividad actividad;
	
	@Column(name = "kcal_objetivo")
	private int kcalObjetivo;
	
	private double proteinas;
	private double carbohidratos;
	private double grasas;

	@ManyToOne
	@JoinColumn(name = "email")
	private Usuario usuario;
}
