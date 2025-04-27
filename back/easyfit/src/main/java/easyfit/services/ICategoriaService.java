package easyfit.services;

import java.util.List;

import easyfit.models.dtos.admin.CategoriasRecuento;
import easyfit.models.entities.Categoria;

public interface ICategoriaService extends IGenericCrud<Categoria, Integer>{
	
	Categoria crearCategoria(Categoria categoria);
	
	Categoria modificarCategoria(int idCategoria, Categoria categoria);
	
	void eliminarCategoria(int idCategoria);
	
	List<CategoriasRecuento> listarCategoriasConRecuento();
}
