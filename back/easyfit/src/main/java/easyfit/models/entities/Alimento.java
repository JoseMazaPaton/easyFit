package easyfit.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import easyfit.models.dtos.AlimentoDto;
import easyfit.models.dtos.AlimentoEnComidaDto;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "idAlimento")
@Entity
@Table(name = "alimentos")
public class Alimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_alimento")
	private int idAlimento;

	private String nombre;

	private String marca;

	private int kcal;
	private double proteinas;
	private double carbohidratos;
	private double grasas;

	@Column(name = "unidad_medida")
	private String unidadMedida;


	//EXPLICACIÓN DE ESTA RELACIÓN QUE PUEDE SER UN POCO CONFUSA:
		// Relación con el usuario que creó este alimento.
		// Esta relación permite saber qué usuario ha creado el alimento,
		// guardando su email como referencia en la base de datos 
	@ManyToOne(optional = false)
	@JoinColumn(name = "creado_por", referencedColumnName = "email", nullable = false)
	private Usuario creadoPor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categoria categoria;
	
	
	
	
}
