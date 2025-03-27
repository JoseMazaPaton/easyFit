package easyfit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import easyfit.exceptions.ResourceNotFoundException;
import easyfit.models.entities.Rol;
import easyfit.repositories.IRolRepository;

@Service
public class RolImplService implements IRolService{
	
	@Autowired
	private IRolRepository rolRepository;

	@Override
	public List<Rol> findAll() {
		return rolRepository.findAll();
	}

	@Override
	public Rol findById(Integer id) {
		return rolRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(("Rol no encontrado con ID: " + id)));
	}

	@Override
	public Rol updateOne(Rol entity) {
		if (rolRepository.existsById(entity.getIdRol())) {
			throw new ResourceNotFoundException("Rol con ID: " + entity.getIdRol() + " no existe");
		}
		return rolRepository.save(entity);
	}

	@Override
	public Rol insertOne(Rol entity) {
		if (!rolRepository.existsById(entity.getIdRol())) {
			throw new ResourceNotFoundException("Rol con ID: " + entity.getIdRol() + " no existe");
		}
		return rolRepository.save(entity);
	}

	@Override
	public void deleteOne(Integer id) {
		if (!rolRepository.existsById(id)) {
			throw new ResourceNotFoundException("Rol con ID: " + id + " no existe");
		}
		try {
			rolRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
	        throw new IllegalStateException("No se puede eliminar el usuario porque está asociado a otros registros.");
	    } catch (Exception e) {
	        throw new RuntimeException("Error inesperado al eliminar el usuario.");
	    }
	}

}
