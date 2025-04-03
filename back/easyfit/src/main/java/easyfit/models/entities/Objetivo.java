package easyfit.models.entities;

import java.io.Serializable;
import easyfit.models.enums.Actividad;
import easyfit.models.enums.ObjetivoUsuario;
import easyfit.models.enums.OpcionPeso;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
	@Column(name = "objetivo_usuario", nullable = false)
	private ObjetivoUsuario objetivoUsuario;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "opcion_peso", nullable = false)
	private OpcionPeso opcionPeso;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "actividad", nullable = false)
	private Actividad actividad;
	
	
	// ANOTACIONES RELACIONES DE OBJETIVO ===========================================================================

	// Esta parte dice que cada objetivo pertenece a un solo usuario.
	// Aquí es donde se guarda la clave (email) que conecta con el usuario.
	// Como solo puede haber un objetivo por usuario, usamos @OneToOne ( de momento en el futuro pensaremos en cambiarlo)
	// Esta es la parte que gestiona porque tiene la fk en la bbdd.
	@OneToOne
	@JoinColumn(name = "email", unique = true)
	private Usuario usuario;


	

}
