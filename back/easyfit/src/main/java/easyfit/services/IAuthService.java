package easyfit.services;

import easyfit.models.dtos.auth.LoginRequestDto;
import easyfit.models.dtos.auth.LoginResponseDto;
import easyfit.models.dtos.auth.RegistroRequestDto;
import easyfit.models.dtos.auth.RegistroResponseDto;
import easyfit.models.dtos.auth.UsuarioPasswordDto;
import easyfit.models.entities.Usuario;
import jakarta.validation.Valid;

public interface IAuthService extends IGenericCrud<Usuario, String>{
	
	LoginResponseDto login(LoginRequestDto loginDto);
	
	RegistroResponseDto altaUsuario (RegistroRequestDto registroDto);

}