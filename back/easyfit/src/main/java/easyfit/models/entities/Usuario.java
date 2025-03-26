package easyfit.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import easyfit.models.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@EqualsAndHashCode(of="email")
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="email")
	private String email;
	
	private String password;
	
	private String nombre;
	
	private int edad;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "sexo", nullable = false) Sexo sexo;
	
	private Double altura;
	
	private boolean suspendida;
	
	@ManyToOne
	@JoinColumn(name="idRol")
	private Rol idRol;

	@Column(name="created_at")
	private LocalDate createdAt;
	
	// Relación muchos a muchos entre Usuario y Alimento.
	// Representa los alimentos que un usuario ha marcado como favoritos.
	// Esta relación se guarda en la tabla intermedia "favoritos".
	@ManyToMany
	@JoinTable(name = "favoritos",joinColumns = @JoinColumn(name = "email"),inverseJoinColumns = @JoinColumn(name = "id_alimento"))
	private List<Alimento> alimentosFavoritos;


}
