package easyfit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import easyfit.exceptions.ResourceNotFoundException;
import easyfit.models.entities.Usuario;
import easyfit.repositories.IUsuarioRepository;

@Service
public class UsuarioImplService implements IUsuarioService{
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
		}

	@Override
	public Usuario findById(String id) {
		return usuarioRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(("Usuario no encontrado con ID: " + id)));
	}

	@Override
	public Usuario updateOne(Usuario entity) {
		 if (usuarioRepository.existsById(entity.getEmail())) {
		        throw new ResourceNotFoundException("Usuario con ID: " + entity.getEmail() + " no existe");
		    }
		    return usuarioRepository.save(entity);
	}

	@Override
	public Usuario insertOne(Usuario entity) {
	    if (!usuarioRepository.existsById(entity.getEmail())) {
	        throw new ResourceNotFoundException("Usuario con ID: " + entity.getEmail() + " no existe");
	    }
	    return usuarioRepository.save(entity);
	}

	@Override
	public void deleteOne(String id) {
	    if (!usuarioRepository.existsById(id)) {
	        throw new ResourceNotFoundException("Usuario con ID: " + id + " no existe");
	    }
	    try {
	       usuarioRepository.deleteById(id);
	    } catch (DataIntegrityViolationException e) {
	        throw new IllegalStateException("No se puede eliminar el usuario porque está asociado a otros registros.");
	    } catch (Exception e) {
	        throw new RuntimeException("Error inesperado al eliminar el usuario.");
	    }
	}
}
