package easyfit.services;

import easyfit.models.dtos.auth.LoginRequestDto;
import easyfit.models.dtos.auth.LoginResponseDto;
import easyfit.models.dtos.auth.RegistroRequestDto;
import easyfit.models.dtos.auth.RegistroResponseDto;
import easyfit.models.entities.Usuario;

public interface IAuthService extends IGenericCrud<Usuario, String>{
	
	LoginResponseDto login(LoginRequestDto loginDto);
	
	RegistroResponseDto altaUsuario (RegistroRequestDto registroDto);

}