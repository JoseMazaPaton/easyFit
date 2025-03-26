package easyfit.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "idConsumo")
@Entity
@Table(name = "consumo_diario")
public class ConsumoDiario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consumo")
    private int idConsumo;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "kcal_consumidas", nullable = false)
    private int kcalConsumidas;
    
    private double proteinas;
    private double carbohidratos;
    private double grasas;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    private Usuario usuario;
}
