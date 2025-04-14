package easyfit.restcontroller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import easyfit.models.dtos.auth.UsuarioPasswordDto;
import easyfit.models.dtos.auth.UsuarioResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.services.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los Usuarios de Easyfit.")
@CrossOrigin(origins = "*")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;

	
	@Autowired 
	private ModelMapper mapper;
	
	
	//RUTA CON METODO PARA VER DATOS DEL PERFIL DEL USUARIO
	@GetMapping("/miperfil")
	@Operation(summary = "Obtener perfil usuario", description = "Obtiene el perfil del Usuario logueado.")
	public ResponseEntity<UsuarioResponseDto> perfilUsuarioAutenticado(){
		
		 //Obtnemos con springsecurity el usuario que hay actualmente en la ruta autenticado
		 Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 
		 //Lo mapeamos a una respuesta dto
		 UsuarioResponseDto respuestaDto = mapper.map(usuario, UsuarioResponseDto.class);
		 
		return ResponseEntity.ok(respuestaDto);
	}
	

	//RUTA CON METODO PARA VER DATOS DEL PERFIL DEL USUARIO
	@PutMapping("/miperfil/password")
	@Operation(summary = "Obtener datos del perfil de Usuario", description = "Obtiene los datos de perfil de un Usuario autenticado y permite el cambio de password del mismo. ")
	public ResponseEntity<String> cambiarPassword(@RequestBody @Valid UsuarioPasswordDto dto) {
		//Obtenemos el usuario autenticado
	    Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    usuarioService.cambiarPassword(dto, usuario);

	    return ResponseEntity.ok("Contraseña actualizada correctamente");
	}


	
	


}
