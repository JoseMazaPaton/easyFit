package easyfit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import easyfit.exceptions.ResourceNotFoundException;
import easyfit.models.entities.ConsumoDiario;
import easyfit.repositories.IConsumoDiarioRepository;

@Service
public class ConsumoDiarioImplService implements IConsumoDiarioService{
	
	@Autowired
	private IConsumoDiarioRepository cDiarioRepository;

	@Override
	public List<ConsumoDiario> findAll() {
		return cDiarioRepository.findAll();
	}

	@Override
	public ConsumoDiario findById(Integer id) {
		return cDiarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(("Consumo diario no encontrado con ID: " + id)));
	}

	@Override
	public ConsumoDiario updateOne(ConsumoDiario entity) {
		if (cDiarioRepository.existsById(entity.getIdConsumo())) {
			throw new ResourceNotFoundException("Consumo diario con ID: " + entity.getIdConsumo() + " no existe");
		}
		return cDiarioRepository.save(entity);
	}

	@Override
	public ConsumoDiario insertOne(ConsumoDiario entity) {
		if (!cDiarioRepository.existsById(entity.getIdConsumo())) {
			throw new ResourceNotFoundException("Consumo diario con ID: " + entity.getIdConsumo() + " no existe");
		}
		return cDiarioRepository.save(entity);
	}

	@Override
	public void deleteOne(Integer id) {
		if (!cDiarioRepository.existsById(id)) {
			throw new ResourceNotFoundException("Consumo diario con ID: " + id + " no existe");
		}
		try {
			cDiarioRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
	        throw new IllegalStateException("No se puede eliminar el consumo diario porque está asociado a otros registros.");
	    } catch (Exception e) {
	        throw new RuntimeException("Error inesperado al eliminar el consumo diario.");
	    }
	}

}
