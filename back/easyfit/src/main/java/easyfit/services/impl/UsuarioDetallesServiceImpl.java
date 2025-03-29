package easyfit.services.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import easyfit.models.entities.Usuario;
import easyfit.repositories.IUsuarioRepository;

/**
 * Esta clase la hemos creado para que Spring Security pueda cargar los datos de un usuario
 * cuando alguien intenta iniciar sesión.
 * 
 * Implementa la interfaz UserDetailsService, que es la que Spring usa por defecto
 * para buscar usuarios en la base de datos cuando llega un login.
 * 
 * Lo que hacemos aquí paso a paso es:
 * 
 * 1. Buscar en la base de datos un usuario que tenga el email recibido.
 * 2. Si no existe, se lanza una excepción para avisar que no se ha encontrado.
 * 3. Si el usuario existe pero está suspendido (enabled = 0), también se lanza una excepción.
 * 4. Si todo está bien, se obtiene su rol (por ejemplo: ROLE_CLIENTE, ROLE_ADMON...).
 * 5. Se crea un objeto `User` de Spring Security con el email, la contraseña y el rol.
 * 
 * Este objeto es el que Spring Security usa para verificar credenciales y dar acceso.
 */
@Service
public class UsuarioDetallesServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    /**
     * Método que se ejecuta automáticamente cuando Spring intenta autenticar a un usuario.
     * 
     * @param email El email del usuario que se quiere autenticar.
     * @return UserDetails con la info del usuario si lo encuentra y está habilitado.
     * @throws UsernameNotFoundException si el usuario no existe o está suspendido.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró un usuario con el email: " + email));

        if (usuario.isSuspendida()) {
            throw new UsernameNotFoundException("El usuario está suspendido: " + email);
        }

        // Obtenemos el nombre del rol (por ejemplo "ROLE_ADMON")
        String nombreRol = usuario.getIdRol().getNombre().name();

        // Creamos la autoridad para Spring Security usando ese rol
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(nombreRol);

        // Creamos y devolvemos un usuario de Spring con su email, contraseña y rol
        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
