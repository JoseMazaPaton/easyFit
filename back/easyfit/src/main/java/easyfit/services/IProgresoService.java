package easyfit.services;

import java.util.Map;

import easyfit.models.entities.Progreso;
import easyfit.models.entities.Usuario;

public interface IProgresoService extends IGenericCrud<Progreso, Integer>{
	Map<String, Double> obtenerProgresoPeso(String emailUsuario);
	Progreso registrarNuevoPeso(double peso, Usuario usuario);
}
