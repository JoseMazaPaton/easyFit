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

import easyfit.models.dtos.UsuarioPasswordDto;
import easyfit.models.dtos.UsuarioResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.services.IUsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;

	
	@Autowired 
	private ModelMapper mapper;
	
	
	//RUTA CON METODO PARA VER DATOS DEL PERFIL DEL USUARIO
	@GetMapping("/miperfil")
	public ResponseEntity<UsuarioResponseDto> perfilUsuarioAutenticado(){
		
		 //Obtnemos con springsecurity el usuario que hay actualmente en la ruta autenticado
		 Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 
		 //Lo mapeamos a una respuesta dto
		 UsuarioResponseDto respuestaDto = mapper.map(usuario, UsuarioResponseDto.class);
		 
		return ResponseEntity.ok(respuestaDto);
	}
	

	//RUTA CON METODO PARA VER DATOS DEL PERFIL DEL USUARIO
	@PutMapping("/miperfil/password")
	public ResponseEntity<String> cambiarPassword(@RequestBody @Valid UsuarioPasswordDto dto) {

	    System.out.println("🔐 DTO recibido en controlador: " + dto); // TEMPORAL

	    Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    usuarioService.cambiarPassword(dto, usuario);

	    return ResponseEntity.ok("Contraseña actualizada correctamente");
	}



}
