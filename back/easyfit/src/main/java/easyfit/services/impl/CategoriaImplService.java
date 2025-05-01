package easyfit.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import easyfit.models.dtos.admin.CategoriasRecuento;
import easyfit.models.entities.Alimento;
import easyfit.models.entities.Categoria;
import easyfit.repositories.IAlimentoRepository;
import easyfit.repositories.ICategoriaRepository;
import easyfit.services.ICategoriaService;
import jakarta.transaction.Transactional;
@Service
public class CategoriaImplService extends GenericCrudServiceImpl<Categoria, Integer> implements ICategoriaService{
	
	@Autowired
	private ICategoriaRepository categoriaRepository;
	
	@Autowired
	private IAlimentoRepository alimentoRepository;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected ICategoriaRepository getRepository() {
		return categoriaRepository;
	}

	@Override
    public List<CategoriasRecuento> listarCategoriasConRecuento() {
        return categoriaRepository.findAllCategoriasConTotalAlimentos();
    }

	
	@Override
	public Categoria crearCategoria(Categoria categoria) {
		// Validación básica
        if(categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }
        
        return categoriaRepository.save(categoria);
    }

	@Override
	public Categoria modificarCategoria(int idCategoria, Categoria categoria) {
	    Categoria existente = categoriaRepository.findById(idCategoria)
	        .orElseThrow(() -> new NoSuchElementException("Categoría no encontrada"));
	    
	    // Validación y actualización del nombre
	    if(categoria.getNombre() == null || categoria.getNombre().isBlank()) {
	        throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
	    }
	    
	    existente.setNombre(categoria.getNombre());
	    return categoriaRepository.save(existente);
	}

	//METODO PARA ELIMINAR LAS CATEGORIAS
	@Transactional  //Por si falla algo que no continue eliminando
	@Override
	public void eliminarCategoria(int idCategoria) {
	    
	    //Listamos los alimentos que tiene esa categoria
	    List<Alimento> listaAlimentos = alimentoRepository.findByCategoria_idCategoria(idCategoria);
	    
	    //Eliminamos todos los alimentos que contiene
	    alimentoRepository.deleteAll(listaAlimentos);
	    
	   try {
			//Comprobamos que la categoria existe primero
		    if (!categoriaRepository.existsById(idCategoria)) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la categoria: " + idCategoria);
		    } else {
		    	categoriaRepository.deleteById(idCategoria);
		    }
	   } catch (Exception e) {
		   throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se puede eliminar la categoria, por integridad de la BBDD");
	   }
	    
	}

	

}
