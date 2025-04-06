package easyfit.models.enums;

import lombok.Getter;

@Getter
public enum OpcionPeso {

    // Opciones que representan los kg que el usuario quiere perder o ganar por semana
	// separado en dos descripcion y kg, el int nos hace falta para calcular los kg
	
    LIGERO("Ligero", 0.25),     // 0.25 kg por semana
    MODERADO("Moderado", 0.50), // 0.50 kg por semana
    INTENSO("Intenso", 0.75),   // 0.75 kg por semana
    MANTENER("Mantener", 0.0); // 1.00 kg por semana

    private final String descripcion; // Descripción para mostrar al usuario
    private final double valorKg;     // Valor real en kg para usar en los cálculos


    OpcionPeso(String descripcion, double valorKg) {
        this.descripcion = descripcion;
        this.valorKg = valorKg;
    }
}
