package easyfit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import easyfit.models.entities.Usuario;
import easyfit.repositories.IUsuarioRepository;
import easyfit.services.IUsuarioService;

@Service
public class UsuarioImplService extends GenericCrudServiceImpl<Usuario,String> implements IUsuarioService{
	
	@Autowired
	private IUsuarioRepository usuarioRepository;


	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected IUsuarioRepository getRepository() {
		return usuarioRepository;
	}



}
