package easyfit.services;

import easyfit.models.dtos.LoginRequestDto;
import easyfit.models.dtos.LoginResponseDto;
import easyfit.models.dtos.RegistroRequestDto;
import easyfit.models.dtos.RegistroResponseDto;
import easyfit.models.entities.Usuario;

public interface IAuthService extends IGenericCrud<Usuario, String>{
	
	LoginResponseDto login(LoginRequestDto loginDto);
	
	RegistroResponseDto altaUsuario (RegistroRequestDto registroDto);
	
}
