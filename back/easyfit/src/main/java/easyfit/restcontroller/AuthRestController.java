package easyfit.restcontroller;

import java.util.Map;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import easyfit.models.dtos.auth.LoginRequestDto;
import easyfit.models.dtos.auth.LoginResponseDto;
import easyfit.models.dtos.auth.RegistroRequestDto;
import easyfit.models.dtos.auth.RegistroResponseDto;


import easyfit.models.entities.Usuario;
import easyfit.services.IAuthService;
import easyfit.services.IProgresoService;
import easyfit.services.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Métodos relacionados con la autenticación de usuarios y administradores a la plataforma de Easyfit.")
@CrossOrigin(origins = "*")
public class AuthRestController {

    @Autowired
    private IAuthService authService;
    
    @Autowired
    private IProgresoService progresoService;
    
    
    @Autowired
    private IUsuarioService usuarioService;


  //METODO CON RUTA PARA INICIAR SESION
    @Operation(summary = "Login de usuario", description = "Autentica un usuario con sus credenciales y devuelve un token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Schema(description = "Objeto con email y contraseña para autenticación", required = true)
    												@RequestBody @Valid LoginRequestDto loginDto) {
    	
    	//Hacemos login con el metodo del service y guardamos las respuesta Dto que devuelve
        LoginResponseDto response = authService.login(loginDto);
        
        return ResponseEntity.ok(response);
    }

    

    //METODO CON RUTA PARA CERRAR SESIÓN
    @Operation(summary = "Logout de usuario", description = "Cierra la sesión del usuario eliminando su contexto de seguridad.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Logout exitoso",
                     content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Usuario no autenticado")
    })
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
    	
        // Limpiamos el contexto de seguridad (borra el usuario autenticado actual)
        SecurityContextHolder.clearContext();

        // Devolvemos un mensaje de exito
        return ResponseEntity.ok(
            Map.of("mensaje", "Sesión cerrada correctamente")
        );
    }
    
    
    //METODO CON RUTA PARA REGISTRAR UN USUARIO
    @Operation(summary = "Registro de usuario", description = "Crea un nuevo usuario en el sistema con los datos proporcionados.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistroResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Datos de registro inválidos"),
        @ApiResponse(responseCode = "409", description = "Usuario ya existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/registro")
    public ResponseEntity<RegistroResponseDto> registroUsuario(@Schema(description = "Datos para el registro de un nuevo usuario", required = true)
    															@RequestBody @Valid RegistroRequestDto registroDto) {
    
    	//Damos de alta el usuario,los objetivos y guardamos la respuesta con el metodo del servicio
    	//Todas las excepciones se controlan en el service tambien
    	RegistroResponseDto respuesta = authService.altaUsuario(registroDto);
    	
    	Usuario usuario = usuarioService.findById(registroDto.getUsuario().getEmail());
    	progresoService.registrarNuevoPeso(registroDto.getObjetivo().getPesoActual(), usuario); 
    		 	

    	return ResponseEntity.ok(respuesta);
    	
    }
    
    @Operation(
    	    summary = "Comprobar si un email ya está registrado",
    	    description = "Devuelve true si el email está disponible para registro, false si ya está en uso."
    	)
    	@ApiResponses(value = {
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Resultado de la disponibilidad",
    	        content = @Content(mediaType = "application/json")
    	    ),
    	    @ApiResponse(
    	        responseCode = "500",
    	        description = "Error interno del servidor",
    	        content = @Content
    	    )
    	})
    	@GetMapping("/comprobaremail")
    	public ResponseEntity<?> comprobarEmailDisponible(
    	    @Parameter(description = "Email que se desea comprobar", required = true)
    	    @RequestParam String email
    	) {
    	    try {
    	        boolean existe = authService.emailExiste(email);
    	        return ResponseEntity.ok(Map.of("disponible", !existe));
    	    } catch (Exception e) {
    	        return ResponseEntity.internalServerError()
    	                .body(Map.of("error", "Error al comprobar el email", "detalle", e.getMessage()));
    	    }
    	}




}