package easyfit.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import easyfit.models.dtos.alimentos.AlimentoDto;
import easyfit.models.dtos.alimentos.AlimentoEnComidaDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "idAlimento")
@Entity
@Table(name = "alimentos")
@Schema(description = "Entidad que representa un alimento")
public class Alimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID único del alimento", example = "1")
	@Column(name = "id_alimento")
	private int idAlimento;
	
	@Schema(description = "Nombre del Alimento", example = "Arroz")
	private String nombre;
	
	@Schema(description = "Marca del alimento", example = "La Fallera")
	private String marca;
	
	@Schema(description = "Calorías que contiene el Alimento", example = "300")
	private int kcal;
	@Schema(description = "Proteínas que contiene el Alimento", example = "300")
	private double proteinas;
	@Schema(description = "Carbohidratos que contiene el Alimento", example = "300")
	private double carbohidratos;
	@Schema(description = "Grasas que contiene el Alimento", example = "300")
	private double grasas;

	@Column(name = "unidad_medida")
	@Schema(description = "Unidad de medida para el Alimento", example = "Gramos")
	private String unidadMedida;


	//EXPLICACIÓN DE ESTA RELACIÓN QUE PUEDE SER UN POCO CONFUSA:
		// Relación con el usuario que creó este alimento.
		// Esta relación permite saber qué usuario ha creado el alimento,
		// guardando su email como referencia en la base de datos 
	@ManyToOne(optional = false)
	@JoinColumn(name = "creado_por", referencedColumnName = "email", nullable = false)
	@Schema(description = "Usuario creador de Alimento", example = "frank@gmail.com")
	private Usuario creadoPor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_categoria", nullable = false)
	@Schema(description = "Categoría del Alimento", example = "Verdura")
	private Categoria categoria;
	
	
	
	
}
