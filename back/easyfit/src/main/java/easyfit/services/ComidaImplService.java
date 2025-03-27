package easyfit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import easyfit.exceptions.ResourceNotFoundException;
import easyfit.models.entities.Comida;
import easyfit.repositories.IComidaRepository;

@Service
public class ComidaImplService implements IComidaService{

	@Autowired
	private IComidaRepository comidaRepository;
	@Override
	public List<Comida> findAll() {
		return comidaRepository.findAll();
	}

	@Override
	public Comida findById(Integer id) {
		return comidaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(("Comida no encontrada por ID: " + id)));
	}

	@Override
	public Comida updateOne(Comida entity) {
		if (comidaRepository.existsById(entity.getIdComida())) {
			throw new ResourceNotFoundException("Comida con ID: " + entity.getIdComida() + " no existe");
		}
		return comidaRepository.save(entity);
	}

	@Override
	public Comida insertOne(Comida entity) {
		if (!comidaRepository.existsById(entity.getIdComida())) {
			throw new ResourceNotFoundException("Comida con ID: " + entity.getIdComida() + " no existe");
		}
		return comidaRepository.save(entity);
	}

	@Override
	public void deleteOne(Integer id) {
		if (!comidaRepository.existsById(id)) {
			throw new ResourceNotFoundException("Comida con ID: " + id + " no existe");
		}
		try {
			comidaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
	        throw new IllegalStateException("No se puede eliminar la comida porque está asociado a otros registros.");
	    } catch (Exception e) {
	        throw new RuntimeException("Error inesperado al eliminar la comida.");
	    }
		
	}

}
