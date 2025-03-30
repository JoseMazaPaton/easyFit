package easyfit.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import easyfit.models.entities.Categoria;
import easyfit.repositories.ICategoriaRepository;
import easyfit.services.ICategoriaService;
@Service
public class CategoriaImplService extends GenericCrudServiceImpl<Categoria, Integer> implements ICategoriaService{
	
	@Autowired
	private ICategoriaRepository categoriaRepository;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected ICategoriaRepository getRepository() {
		return categoriaRepository;
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

	@Override
	public void eliminarCategoria(int idCategoria) {
	    if (!categoriaRepository.existsById(idCategoria)) {
	        throw new NoSuchElementException("Categoría no encontrada con ID: " + idCategoria);
	    }
	    
	    categoriaRepository.deleteById(idCategoria);
	}

	@Override
	public List<Categoria> obtenerTodasCategorias() {
		// TODO Auto-generated method stub
		return categoriaRepository.findAll();
	}
	

}
