package easyfit.restcontroller;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import easyfit.auth.JwtUtils;
import easyfit.models.dtos.LoginRequestDto;
import easyfit.models.dtos.LoginResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.services.IUsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * METODO CON RUTA PARA INICIAR SESION
     * Autenticamos al usuario si todo es correctos genera un token JWT 
     * el metodo acaba devolviendo los datos del ususario + el token
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginDto) {
        try {
            // Autenticamos el usuario con la info del Dto que hemos creado con el AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Recuperamos el usuario desde la base de datos
            Usuario usuario = usuarioService.findById(loginDto.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

            // Generamos el token JWT con JwtUtils 
            String token = jwtUtils.generateToken(usuario);

            // Mapeamos el usuario al dto de respuesta que hemos creado.( para evitar que salgan las relaciones)
            //y devolvemos los datos del usuario + el token 
            LoginResponseDto response = modelMapper.map(usuario, LoginResponseDto.class);
            response.setToken(token);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Email o contraseña incorrectos");
        }
    }
    
    /**
     * METODO CON RUTA PARA CERRAR SESIÓN
     * 
     * Aunque trabajamos con JWT y no se guarda una sesión en el servidor,
     * este método limpia el contexto de seguridad de Spring para que no quede el usuario en memoria.
     * 
     * En Angular debemos borra el token que hay en LocalStorage 
     * 
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        // Limpiamos el contexto de seguridad (borra el usuario autenticado actual)
        SecurityContextHolder.clearContext();

        // Devolvemos un mensaje de exito
        return ResponseEntity.ok(
            Map.of("mensaje", "Sesión cerrada correctamente")
        );
    }

}