package easyfit.services;

import easyfit.models.dtos.auth.UsuarioPasswordDto;
import easyfit.models.entities.Usuario;

public interface IUsuarioService extends IGenericCrud<Usuario, String> {

	void cambiarPassword(UsuarioPasswordDto password, Usuario usuario);
	
}
