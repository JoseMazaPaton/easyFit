package easyfit.services.impl;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import easyfit.auth.JwtUtils;
import easyfit.models.dtos.auth.LoginRequestDto;
import easyfit.models.dtos.auth.LoginResponseDto;
import easyfit.models.dtos.auth.RegistroRequestDto;
import easyfit.models.dtos.auth.RegistroResponseDto;
import easyfit.models.dtos.auth.UsuarioResponseDto;
import easyfit.models.dtos.objetivo.ObjetivoResponseDto;
import easyfit.models.dtos.valornutricional.ValorNutriconalResponseDto;
import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.repositories.IUsuarioRepository;
import easyfit.services.IAuthService;
import easyfit.services.IValorNutricionalService;
import jakarta.transaction.Transactional;

@Service
public class AuthImplService extends GenericCrudServiceImpl<Usuario,String> implements IAuthService{
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	
	@Autowired
	private IValorNutricionalService objetivoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper mapper;
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private EmailServiceImpl emailService;


	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected IUsuarioRepository getRepository() {
		return usuarioRepository;
	}

	//METODO PARA LOGIN DE UN USUARIO
	@Override
	public LoginResponseDto login(LoginRequestDto loginDto) {
		try {
			// Autenticamos el usuario con la info del Dto que hemos creado con el AuthenticationManager
		    Authentication authentication = authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		    SecurityContextHolder.getContext().setAuthentication(authentication);
		    
		    // Recuperamos el usuario desde la base de datos
		    Usuario usuario = findById(loginDto.getEmail());
		    
		    // Generamos el token JWT con JwtUtils 
		    String token = jwtUtils.generateToken(usuario);
		    
	        // Mapeamos el usuario al dto de respuesta que hemos creado.( para evitar que salgan las relaciones)
	        //y devolvemos los datos del usuario + el token 
		    LoginResponseDto response = mapper.map(usuario, LoginResponseDto.class);
		    response.setToken(token);
		    
		    return response;

		} catch (BadCredentialsException e) {
		    throw new BadCredentialsException("Email o contraseña incorrectos");
		} catch (UsernameNotFoundException e) {
		    throw new UsernameNotFoundException("El usuario no existe");
		} catch (Exception e) {
		    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado en el login: " + e.getMessage());
		}
	}

	
	// METODO PARA DAR DE ALTA UN USUARIO CON ROL_USUARIO 
	@Override
	@Transactional // Esto lo ponemos por si hay algun fallo que se revierta todo (por ejemplo se guarda usuario pero el resto falla)
	public RegistroResponseDto altaUsuario(RegistroRequestDto registroDto) {
	    try {
	        if (usuarioRepository.existsById(registroDto.getUsuario().getEmail())) {
	            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está dado de alta.");
	        }

	        // Guardamos la contraseña para encriptarla
	        // También sirve en sí para más adelante si quisiera implementar la lógica de enviarla por email
	        String contraseña = registroDto.getUsuario().getPassword();

	        // Mapeamos usuario y objetivo a sus entidades
	        Usuario usuario = mapper.map(registroDto.getUsuario(), Usuario.class);
	        Objetivo objetivo = mapper.map(registroDto.getObjetivo(), Objetivo.class);

	        // Agregamos a cada entidad las relaciones (bidireccional)
	        // Encriptamos la contraseña y la añadimos
	        // Le añadimos la fechaDeRegistro
	        // El rol no hace falta porque lo hemos puesto por defecto como = ROL_USUARIO en la entidad
	        usuario.setPassword(passwordEncoder.encode(contraseña));
	        usuario.setFechaRegistro(LocalDate.now());
	        usuario.setObjetivo(objetivo);
	        objetivo.setUsuario(usuario);   

	        // Guardamos los datos del usuario (se guarda también objetivo por cascada)
	        usuarioRepository.save(usuario);
	        
	        //Enviar credenciales por correo
            // emailService.enviarCredenciales(usuario.getEmail(), contraseña);  -> para cuando se despliegue

	        // Calculamos las Kcal (los macros se calculan automáticamente por trigger en BBDD)
	        ValorNutriconalResponseDto calculo = objetivoService.registroMacrosYKcal(usuario.getEmail());

	        // Después volvemos a mapearlo en forma de respuestaDto
	        UsuarioResponseDto usuarioDto = mapper.map(usuario, UsuarioResponseDto.class);
	        ObjetivoResponseDto objetivoDto = mapper.map(objetivo, ObjetivoResponseDto.class);
	        objetivoDto.setValores(calculo); // Ahora usamos un DTO más completo con kcal, macros y porcentajes

	        // Generamos el token y se lo añadimos a la respuesta
	        // ESTO ES OPCIONAL-- es por si queremos iniciar sesión nada más registrarnos
	        String token = jwtUtils.generateToken(usuario);

	        // Devolvemos un Dto para la respuesta del controller
	        return new RegistroResponseDto(usuarioDto, objetivoDto, token);

	    } catch (IllegalArgumentException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al registrar el usuario: " + e.getMessage());
	    }
}



}
