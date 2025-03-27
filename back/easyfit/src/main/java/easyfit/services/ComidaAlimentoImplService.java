package easyfit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import easyfit.exceptions.ResourceNotFoundException;
import easyfit.models.entities.ComidaAlimento;
import easyfit.repositories.IComidaAlimentoRepository;

@Service
public class ComidaAlimentoImplService implements IComidaAlimentoService{

	@Autowired
	private IComidaAlimentoRepository cAlimentoRepository;
	@Override
	public List<ComidaAlimento> findAll() {
		return cAlimentoRepository.findAll();
	}

	@Override
	public ComidaAlimento findById(Integer id) {
		return cAlimentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(("Comida y su alimento no encontrado con ID: " + id)));
	}

	@Override
	public ComidaAlimento updateOne(ComidaAlimento entity) {
		if (cAlimentoRepository.existsById(entity.getIdComidaAlimento())) {
			throw new ResourceNotFoundException("Comida alimento con ID: " + entity.getIdComidaAlimento() + " no existe");
		}
		return cAlimentoRepository.save(entity);
	}

	@Override
	public ComidaAlimento insertOne(ComidaAlimento entity) {
		if (!cAlimentoRepository.existsById(entity.getIdComidaAlimento())) {
			throw new ResourceNotFoundException("Comida alimento con ID: " + entity.getIdComidaAlimento() + " no existe");
		}
		return cAlimentoRepository.save(entity);
	}

	@Override
	public void deleteOne(Integer id) {
		if (!cAlimentoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Comida Alimento con ID: " + id + " no existe");
		}
		try {
			cAlimentoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
	        throw new IllegalStateException("No se puede eliminar la comida con alimento porque está asociado a otros registros.");
	    } catch (Exception e) {
	        throw new RuntimeException("Error inesperado al eliminar la comida con alimento.");
	    }
		
	}

}
