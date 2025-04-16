package easyfit.services;

import java.time.LocalDate;
import java.util.List;

import easyfit.models.dtos.auth.ResumenComidaDto;
import easyfit.models.dtos.comida.ComidaDiariaDto;
import easyfit.models.entities.Comida;
import easyfit.models.entities.Usuario;

public interface IComidaService extends IGenericCrud<Comida, Integer>{
	
	 List<ComidaDiariaDto> obtenerComidasDelDia(LocalDate fecha, String emailUsuario);
	 Comida crearComida(Comida comida);
	 void agregarAlimentoAComida(int idComida, int idAlimento, double cantidad);
	 void eliminarAlimentoDeComida(int idComida, int idAlimento);
	 void actualizarCantidadAlimento(int idComida, int idAlimento, double nuevaCantidad);
	 void eliminarComida(int idComida, Usuario usuario);
	 ResumenComidaDto obtenerResumenComida(int idComida, Usuario usuario);

}
