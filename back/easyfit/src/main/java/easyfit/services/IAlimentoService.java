package easyfit.services;

import java.util.List;

import easyfit.models.dtos.AlimentoDto;
import easyfit.models.entities.Alimento;

public interface IAlimentoService extends IGenericCrud<Alimento, Integer>{
	
	void eliminarAlimento(int idAlimento);
	Alimento crearAlimento(Alimento alimento);
	Alimento modificarAlimento(int idAlimento, Alimento alimentoActualizado);
	List<AlimentoDto> buscarPorNombre(String nombre);
	List<AlimentoDto> buscarPorUsuario(String email);
}
