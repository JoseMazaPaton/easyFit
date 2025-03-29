package easyfit.models.enums;


public enum AjustePeso {
	
	 //Enumeración que representa las únicas opciones válidas de ajuste de peso por semana (en kilogramos)
	 // Cada constante representa cuántos kg por semana quiere perder o ganar el usuario
	 KG_025(0.25), // 0.25 kg por semana
	 KG_050(0.50), // 0.50 kg por semana
	 KG_075(0.75), // 0.75 kg por semana
	 KG_100(1.00); // 1.00 kg por semana
	
	 // Variable que guarda el valor en kg asociado a cada constante
	 private final double valorKg;
	
	 // Constructor del enum, que asigna el valor correspondiente
	 AjustePeso(double valorKg) {
	     this.valorKg = valorKg;
	 }
	
	 // Método público para acceder al valor en kg (por ejemplo, 0.50)
	 public double getValorKg() {
	     return valorKg;
 }
}
