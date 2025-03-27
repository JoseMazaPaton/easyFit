package easyfit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import easyfit.exceptions.ResourceNotFoundException;
import easyfit.models.entities.Categoria;
import easyfit.repositories.ICategoriaRepository;
@Service
public class CategoriaImplService implements ICategoriaService{
	
	@Autowired
	private ICategoriaRepository categoriaRepository;  
	
	@Override
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	@Override
	public Categoria findById(Integer id) {
		return categoriaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(("Categoria no encontrada con ID: " + id)));
	}

	@Override
	public Categoria updateOne(Categoria entity) {
		if (categoriaRepository.existsById(entity.getIdCategoria())) {
			throw new ResourceNotFoundException("Usuario con ID: " + entity.getIdCategoria() + " no existe");
		}
		return categoriaRepository.save(entity);
	}

	@Override
	public Categoria insertOne(Categoria entity) {
		if (!categoriaRepository.existsById(entity.getIdCategoria())) {
			throw new ResourceNotFoundException("Usuario con ID: " + entity.getIdCategoria() + " no existe");
		}
		return categoriaRepository.save(entity);
	}

	@Override
	public void deleteOne(Integer id) {
		if (!categoriaRepository.existsById(id)) {
			throw new ResourceNotFoundException("Categoria con ID: " + id + " no existe");
		}
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
	        throw new IllegalStateException("No se puede eliminar la categoria porque está asociado a otros registros.");
	    } catch (Exception e) {
	        throw new RuntimeException("Error inesperado al eliminar la categoria.");
	    }

	}

}
