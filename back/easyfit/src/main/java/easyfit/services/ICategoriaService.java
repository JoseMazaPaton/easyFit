package easyfit.services;

import easyfit.models.entities.Categoria;

public interface ICategoriaService extends IGenericCrud<Categoria, Integer>{
	
	Categoria crearCategoria(Categoria categoria);
	Categoria modificarCategoria(int idCategoria, Categoria categoria);
	void eliminarCategoria(int idCategoria);
}
