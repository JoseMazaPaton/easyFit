package easyfit.restcontroller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import easyfit.models.entities.Usuario;
import easyfit.services.IUsuarioService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthRestController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/login")
	public Usuario login() {
		return null;
	}
	
	@PostMapping("/logout")
	public Usuario logout() {
		return null;
	}
	
	@PostMapping("/registro")
	public Usuario registro() {
		return null;
	}
	
	@GetMapping("/me")
	public Usuario obtenerDatosUsuario() {
		return null;
	}
}
