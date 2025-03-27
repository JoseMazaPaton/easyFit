package easyfit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import easyfit.exceptions.ResourceNotFoundException;
import easyfit.models.entities.Objetivo;
import easyfit.repositories.IObjetivoRepository;

@Service
public class ObjetivoImplService implements IObjetivoService{

	@Autowired
	private IObjetivoRepository objetivoRepository;

	@Override
	public List<Objetivo> findAll() {
		return objetivoRepository.findAll();
	}

	@Override
	public Objetivo findById(Integer id) {
		return objetivoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(("Objetivo no encontrado con ID: " + id)));
	}

	@Override
	public Objetivo updateOne(Objetivo entity) {
		if (objetivoRepository.existsById(entity.getIdObjetivo())) {
			throw new ResourceNotFoundException("Objetivo con ID: " + entity.getIdObjetivo() + " no existe");
		}
		return objetivoRepository.save(entity);
	}

	@Override
	public Objetivo insertOne(Objetivo entity) {
		if (!objetivoRepository.existsById(entity.getIdObjetivo())) {
			throw new ResourceNotFoundException("Objetivo con ID: " + entity.getIdObjetivo() + " no existe");
		}
		return objetivoRepository.save(entity);
	}

	@Override
	public void deleteOne(Integer id) {
		if (!objetivoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Objetivo con ID: " + id + " no existe");
		}
		try {
			objetivoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalStateException("No se puede eliminar porque esta asociado a otro registros.");
		} catch (Exception e) {
			throw new RuntimeException("Error insesperado al eliminar el usuario.");
		}
	}
		
	

}
