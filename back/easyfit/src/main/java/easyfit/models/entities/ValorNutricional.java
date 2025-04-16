package easyfit.models.entities;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@EqualsAndHashCode(of="idValores")
@Entity
@Table(name = "valores_nutricionales")
@Schema(description = "Entidad que representa los Valores Nutricionales del Usuario")
public class ValorNutricional implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_valores", nullable = false)
	@Schema(description = "Nº ID de los Valores Nutricionales", example = "1")
    private Integer idValores;

	@Schema(description = "Calorías a consumir como objetivo de Usuario", example = "1800(calorías)")
    @Column(name = "kcal_objetivo", nullable = false)
    private Integer kcalObjetivo;

   //Estos se autocalculan con un TRIGGER en la bbdd, solo están de LECTURA
    private double proteinas;
    private double carbohidratos; 
    private double grasas; 

    // Los porcentajes de macros por defecto van a llevar unos para cuando se cree un usuario
    @Builder.Default
    @Column(name = "porcentaje_proteinas", nullable = false)
    private double porcentajeProteinas = 20;

    @Builder.Default
    @Column(name = "porcentaje_carbohidratos", nullable = false)
    private double porcentajeCarbohidratos = 50 ;

    @Builder.Default
    @Column(name = "porcentaje_grasas", nullable = false)
    private double porcentajeGrasas = 30;

    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Usuario usuario;
    
}