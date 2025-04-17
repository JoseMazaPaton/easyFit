package easyfit.models.entities;
import java.io.Serializable;

import easyfit.models.enums.TipoRol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@EqualsAndHashCode(of="idRol")
@Entity
@Table(name="roles")
@Schema(description = "Entidad que representa el Rol del usuario autenticado.")
public class Rol implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_rol")
	@Schema(description = "Nº de ID del Rol", example = "1")
	private int idRol;
	
	@Builder.Default // Por defecto le agregamos ROL_USUARIO para cuando hagamos un registro
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true)
	@Schema(description = "Representa al Enum Tipo de Rol", example = "ROL_ADMIN, ROL_USUARIO")
	private TipoRol nombre = TipoRol.ROL_USUARIO;
	
}
