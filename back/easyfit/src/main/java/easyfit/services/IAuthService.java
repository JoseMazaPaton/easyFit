package easyfit.services;

import easyfit.models.dtos.LoginRequestDto;
import easyfit.models.dtos.LoginResponseDto;
import easyfit.models.dtos.RegistroRequestDto;
import easyfit.models.dtos.RegistroResponseDto;
import easyfit.models.dtos.UsuarioPasswordDto;
import easyfit.models.entities.Usuario;
import jakarta.validation.Valid;

public interface IAuthService extends IGenericCrud<Usuario, String>{
	
	LoginResponseDto login(LoginRequestDto loginDto);
	
	RegistroResponseDto altaUsuario (RegistroRequestDto registroDto);

}