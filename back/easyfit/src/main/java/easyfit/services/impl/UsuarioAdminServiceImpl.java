package easyfit.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Sexo;
import easyfit.repositories.IUsuarioRepository;
import easyfit.services.IUsuarioAdminService;

public class UsuarioAdminServiceImpl extends GenericCrudServiceImpl<Usuario, String> implements IUsuarioAdminService{

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	protected JpaRepository<Usuario, String> getRepository() {
		// TODO Auto-generated method stub
		return usuarioRepository;
	}

	@Override
	public List<Usuario> findByEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> findBySexo(Sexo sexo) {
		// TODO Auto-generated method stub
		return usuarioRepository.findBySexo(sexo);
	}

	@Override
	public List<Usuario> findByEdad(int edad) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByEdad(edad);
	}

	//Método para la suspensión de la cuenta de usuario.
	@Override
	public Usuario toggleSuspension(String email) {
		 Usuario usuario = usuarioRepository.findById(email)
			        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
			    
			    usuario.setSuspendida(!usuario.isSuspendida());
			    return usuarioRepository.save(usuario);
	}

	
}

