package easyfit.restcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import easyfit.models.dtos.auth.LoginRequestDto;
import easyfit.models.dtos.auth.LoginResponseDto;
import easyfit.models.dtos.auth.RegistroRequestDto;
import easyfit.models.dtos.auth.RegistroResponseDto;
import easyfit.models.dtos.auth.UsuarioResponseDto;
import easyfit.services.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Métodos relacionados con la autenticación de usuarios y administradores a la plataforma de Easyfit.")
@CrossOrigin(origins = "*")
public class AuthRestController {

    @Autowired
    private IAuthService authService;


  //METODO CON RUTA PARA INICIAR SESION
    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Loguea un usuario.")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginDto) {
    	
    	//Hacemos login con el metodo del service y guardamos las respuesta Dto que devuelve
        LoginResponseDto response = authService.login(loginDto);
        
        return ResponseEntity.ok(response);
    }

    

    //METODO CON RUTA PARA CERRAR SESIÓN
    @PostMapping("/logout")
    @Operation(summary = "Logout de usuario", description = "Cierra la sesión del usuario.")
    public ResponseEntity<Map<String, Object>> logout() {
    	
        // Limpiamos el contexto de seguridad (borra el usuario autenticado actual)
        SecurityContextHolder.clearContext();

        // Devolvemos un mensaje de exito
        return ResponseEntity.ok(
            Map.of("mensaje", "Sesión cerrada correctamente")
        );
    }
    
    
    //METODO CON RUTA PARA REGISTRAR UN USUARIO
    @PostMapping("/registro")
    @Operation(summary = "Registro de usuario", description = "Método de registro de nuevo usuario.")
    public ResponseEntity<RegistroResponseDto> registroUsuario(@RequestBody @Valid RegistroRequestDto registroDto) {
    
    	//Damos de alta el usuario,los objetivos y guardamos la respuesta con el metodo del servicio
    	//Todas las excepciones se controlan en el service tambien
    	RegistroResponseDto respuesta = authService.altaUsuario(registroDto);

    	return ResponseEntity.ok(respuesta);
    	
    }




}