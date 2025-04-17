package easyfit.auth;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import easyfit.services.impl.UsuarioDetallesServiceImpl;

@Configuration
public class SpringSecurityConfig {
	/**
	 * Esta clase configura la seguridad de toda la aplicación.
	 *
	 * Lo que hemos hecho aquí es definir cómo se comporta Spring Security al proteger las rutas.
	 *
	 * Paso a paso:
	 * 1. Hemos configurado qué rutas puede usar cualquier usuario (sin estar logueado), y cuáles solo pueden usar ciertos roles:
	 *    - Por ejemplo, para iniciar sesión o registrarse, no hace falta estar autenticado.
	 *    - Las rutas de vacantes, empresas, solicitudes, etc., están protegidas y solo accesibles si tienes el rol adecuado.
	 *
	 * 2. Hemos añadido un filtro (JwtAuthenticationFilter) que revisa el token JWT en cada solicitud.
	 *    - Si el token es válido, se identifica al usuario automáticamente.
	 *    - Si no lo es, usamos JwtAuthenticationEntryPoint para devolver un error 401 (no autorizado).
	 *
	 * 3. Hemos desactivado la seguridad CSRF porque no usamos sesiones, sino tokens JWT.
	 *
	 * 4. También hemos configurado CORS para permitir que el frontend que está en localhost:4200
	 *    pueda hacer peticiones a esta API sin problemas.
	 *
	 * 5. Por último, se define que la app es "stateless", es decir, que no guarda sesión entre peticiones.
	 */

	
	// Inyectamos el filtro JWT que revisa el token en cada solicitud
	@Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	// Inyectamos el manejador de errores de autenticación para devolver un 401 cuando no se autoriza
	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private UsuarioDetallesServiceImpl usuarioDetallesService;

    // Creamos un bean para encriptar contraseñas usando BCrypt 
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuramos el AuthenticationManager que gestiona la autenticación
    // @Bean
    //AuthenticationManager authenticationManager(AuthenticationConfiguration config)
    //        throws Exception {
    //    return config.getAuthenticationManager();
    //}

    // Configuración principal de seguridad
    // Este bloque define las reglas de acceso y la integración de JWT
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF porque usamos JWT y la app es stateless
            .cors(Customizer.withDefaults()) // Usamos configuración CORS por defecto
            .authorizeHttpRequests(authorize -> {
            	
            	authorize
            	
                // AUTH =================================================================================
                .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/registro").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/logout").authenticated()

                // PÚBLICO =================================================================================
                .requestMatchers(HttpMethod.GET, "/alimentos", "/alimentos/**", "/categorias/todas").permitAll()

                // USUARIO AUTENTICADO =================================================================================
                .requestMatchers(HttpMethod.GET, "/usuarios/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")

                .requestMatchers(HttpMethod.GET, "/objetivos/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/dashboard/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")
                
                .requestMatchers(HttpMethod.GET, "/dashboard/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")

                .requestMatchers(HttpMethod.GET, "/progreso/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")
                .requestMatchers(HttpMethod.POST, "/progreso/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")

                .requestMatchers(HttpMethod.GET, "/comidas/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")
                .requestMatchers(HttpMethod.POST, "/comidas/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/comidas/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/comidas/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")

                .requestMatchers(HttpMethod.GET, "/alimentos/mis").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")
                .requestMatchers(HttpMethod.POST, "/alimentos").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/alimentos/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/alimentos/**").hasAnyRole("ROL_USUARIO", "ROL_ADMIN")

                // SOLO ADMIN ==========================================================================================
                .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ROL_ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ROL_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ROL_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ROL_ADMIN")

                // POR DEFECTO =========================================================================================
                .anyRequest().authenticated();


        })
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)) // Usa este manejador si falla la autenticación
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // La app no guarda sesión

        // Agregamos el filtro JWT antes del filtro de autenticación de Spring
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configuración de CORS para permitir peticiones desde el frontend
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Permitir el frontend local
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Métodos permitidos
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Headers permitidos
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    // Configuramos el AuthenticationManager que gestiona la autenticacion
    @SuppressWarnings("removal")
	@Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    	return http.getSharedObject(AuthenticationManagerBuilder.class)
    			.userDetailsService(usuarioDetallesService)
    			.passwordEncoder(passwordEncoder())
    			.and()
    			.build();
    }
}
