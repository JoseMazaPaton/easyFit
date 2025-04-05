package easyfit.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import easyfit.models.dtos.auth.UsuarioPasswordDto;
import easyfit.models.dtos.auth.UsuarioRequestDto;
import easyfit.models.dtos.auth.UsuarioResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.repositories.IUsuarioRepository;
import easyfit.services.IUsuarioService;

@Service
public class UsuarioServiceImpl extends GenericCrudServiceImpl<Usuario, String> implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper mapper;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido
	@Override
	protected IUsuarioRepository getRepository() {
		return usuarioRepository;
	}

	@Override
	public void cambiarPassword(UsuarioPasswordDto password, Usuario usuario) {
		
		 System.out.println("📥 Contraseña en el servicio: " + password.getPassword()); // TEMPORAL
		 
		usuario.setPassword(passwordEncoder.encode(password.getPassword()));
		usuarioRepository.save(usuario);
		
	}




	
	

}
