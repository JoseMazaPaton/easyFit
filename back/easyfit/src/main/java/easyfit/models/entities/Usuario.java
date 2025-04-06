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
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

	@Column(name="fecha_registro")
	private LocalDate fechaRegistro;
	
	
	// ANOTACIONES RELACIONES DE USUARIO =========================================================================
	
	// Relacion inversa entre Usuario y Objetivo (1:1)
	// La FK esta en la tabla Objetivo y alli se gestiona
	// Al decir mappedBy, indicamos que la relación se gestiona desde Objetivo.
	// Esta relación la vamos a usar para que desde el Usuario podamos acceder directamente a su Objetivo con usuario.getObjetivo()
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private Objetivo objetivo;

	
	// Cada usuario tiene un rol (como admin o normal).
	// Muchos usuarios pueden tener el mismo rol.
	@ManyToOne
	@JoinColumn(name="id_rol")
	private Rol idRol;
	
	
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
	private ValorNutricional valorNutricional;

	
	// METODOS PROPIOS =====================================================================================================  
	
	//Hemos añadido este metodo propio bastante util para sacar el nombre porque es algo recurrente y asi reutilizamos codigo
	public String getTipoRol() {
	    return this.idRol != null ? this.idRol.getNombre().name() : null;
	}

	
	// METODOS CLASE USERDETAILS DE SPRINGSECURITY ==========================================================================  
	
	@Transient
	private List<SimpleGrantedAuthority> authorities;
	
    
    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
    	this.authorities = authorities;
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    String rol = getTipoRol();
	    if (rol == null) return Collections.emptyList();
	    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol));
	}


    @Override
    public String getUsername() {
        return this.email;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }



}
