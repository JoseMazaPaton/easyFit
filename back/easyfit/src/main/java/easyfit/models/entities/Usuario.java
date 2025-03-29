package easyfit.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import easyfit.models.enums.Sexo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@EqualsAndHashCode(of="email")
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable, UserDetails{
	
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

	@Column(name="fecha_registro", updatable = false)
	@org.hibernate.annotations.CreationTimestamp
	private LocalDate fechaRegistro;
	
	// ANOTACIONES RELACIONES DE USUARIO =========================================================================
	
	// Esta relacion es la parte inversa entre Usuario y Objetivo
	// La FK esta en la tabla Objetivo y alli se gestiona
	// Esta anotacion de relacion inversa en usuario la usamos para decir cual es el objetivo activo.
	// porque un usuario solo puede tener un obetivo activo.
	// Pero un usuario puede tener varios registros de objetivos en la bddd ( para ver el progreso que es donde se guardan)
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private Objetivo objetivo;

	// Esta relacion (N:M) hace referencia a la tabla intermedia FAVORITO de la bbdd entre Usuario y Alimento
	// La vamos a usar para guardar alimentos favoritos de cada usuario.
	@ManyToMany
	@JoinTable(name = "favoritos",joinColumns = @JoinColumn(name = "email"),inverseJoinColumns = @JoinColumn(name = "id_alimento"))
	private List<Alimento> alimentosFavoritos;
	
	// Cada usuario tiene un rol (como admin o normal).
	// Muchos usuarios pueden tener el mismo rol.
	@ManyToOne
	@JoinColumn(name="idRol")
	private Rol idRol;
	
	
	// Metodos de la clase UserDetails, clase de SpringSecurity ==================================================  
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String nombreRol = idRol.getNombre().name(); 
        return Collections.singletonList(new SimpleGrantedAuthority(nombreRol));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.suspendida;
    }

}
