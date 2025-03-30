package easyfit.services;

import easyfit.models.entities.Alimento;

public interface IAlimentoService extends IGenericCrud<Alimento, Integer>{
	
	void eliminarAlimento(int idAlimento);
	Alimento crearAlimento(Alimento alimento);
	Alimento modificarAlimento(int idAlimento, Alimento alimentoActualizado);
}
