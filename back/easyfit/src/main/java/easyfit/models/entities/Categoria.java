package easyfit.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of="idCategoria")
@Entity
@Table(name="categorias")
@Schema(description = "Entidad que representa una Categoría de un Alimento")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	@Schema(description = "Número de ID de la Categoría de Alimento", example = "1")
    private int idCategoria;
	
	@Schema(description = "Nombre para la Categoría del Alimento", example = "Fruta")
    private String nombre;

}
