package easyfit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import easyfit.exceptions.ResourceNotFoundException;
import easyfit.models.entities.Progreso;
import easyfit.repositories.IProgresoRepository;

@Service
public class ProgresoImplRepository implements IProgresoService{
	
	@Autowired
	private IProgresoRepository progresoRepository;
	@Override
	public List<Progreso> findAll() {
		return progresoRepository.findAll();
	}

	@Override
	public Progreso findById(Integer id) {
		return progresoRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(("Progreso no encontrado con ID: " + id)));
	}

	@Override
	public Progreso updateOne(Progreso entity) {
		if (progresoRepository.existsById(entity.getIdProgreso())) {
			throw new ResourceNotFoundException("Progreso con ID: " + entity.getIdProgreso() + " no existe");
		}
		return progresoRepository.save(entity);
	}

	@Override
	public Progreso insertOne(Progreso entity) {
		if (!progresoRepository.existsById(entity.getIdProgreso())) {
			throw new ResourceNotFoundException("Progreso con ID: " + entity.getIdProgreso() + " no existe");
		}
		return progresoRepository.save(entity);
	}

	@Override
	public void deleteOne(Integer id) {
		if (!progresoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Progeso con ID: " + id + " no existe");
		}
		try {
			progresoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
	        throw new IllegalStateException("No se puede eliminar el progreso porque está asociado a otros registros.");
	    } catch (Exception e) {
	        throw new RuntimeException("Error inesperado al eliminar el progreso.");
	    }
	}

}
