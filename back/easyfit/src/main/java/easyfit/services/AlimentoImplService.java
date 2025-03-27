package easyfit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import easyfit.exceptions.ResourceNotFoundException;
import easyfit.models.entities.Alimento;
import easyfit.repositories.IAlimentoRepository;

@Service
public class AlimentoImplService implements IAlimentoService{

	@Autowired
	private IAlimentoRepository alimentoRepository;
	
	@Override
	public List<Alimento> findAll() {
		return alimentoRepository.findAll();
	}

	@Override
	public Alimento findById(Integer id) {	
		return alimentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(("Alimento no encontrado con ID: " + id)));
	}

	@Override
	public Alimento updateOne(Alimento entity) {
		if (alimentoRepository.existsById(entity.getIdAlimento())) {
			throw new ResourceNotFoundException("Alimento con ID: " + entity.getIdAlimento() + " no existe.");
		}
		return alimentoRepository.save(entity);
	}

	@Override
	public Alimento insertOne(Alimento entity) {
		if (!alimentoRepository.existsById(entity.getIdAlimento())) {
			throw new ResourceNotFoundException("Alimento con ID: " + entity.getIdAlimento() + " no existe.");
		}
		return alimentoRepository.save(entity);
	}

	@Override
	public void deleteOne(Integer id) {
		if (!alimentoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Alimento con ID: " + id + " no existe.");
		}
		try {
			alimentoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalStateException("No se puede eliminar el alimento porque está asociado a otros registros.");
		} catch (Exception e) {
			throw new RuntimeException("Error inesperado al eliminar el alimento.");
		}
	}

}
