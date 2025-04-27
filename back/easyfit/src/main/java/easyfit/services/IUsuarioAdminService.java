package easyfit.services;
import java.util.List;

import easyfit.models.dtos.admin.UserResumenDto;
import easyfit.models.dtos.admin.UsuarioAdminListaDto;
import easyfit.models.dtos.auth.UsuarioResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Sexo;

public interface IUsuarioAdminService extends IGenericCrud<Usuario, String>{

	List<Usuario> findByEmail(String email);
	
	List<Usuario> findBySexo(Sexo sexo);
	
	List<Usuario> findByEdad(int edad);
	
	UsuarioAdminListaDto cambiarEstadoUsuario(String email);
	
	UserResumenDto obtenerResumenUsuarios();

	List<UsuarioAdminListaDto> obtenerUsuarios();
}


