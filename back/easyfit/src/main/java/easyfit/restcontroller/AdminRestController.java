package easyfit.restcontroller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import easyfit.models.dtos.admin.UserResumenDto;
import easyfit.models.dtos.admin.UsuarioAdminListaDto;
import easyfit.models.dtos.auth.UsuarioResponseDto;
import easyfit.models.entities.Alimento;
import easyfit.models.entities.Categoria;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Sexo;
import easyfit.services.IAlimentoService;
import easyfit.services.ICategoriaService;
import easyfit.services.IUsuarioAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;



@RestController
@RequestMapping("/admin")
@Tag(name = "Administradores", description = "Operaciones relacionadas con los administradores de Easyfit.")
@CrossOrigin(origins = "*")
public class AdminRestController {
	
	@Autowired
	private IUsuarioAdminService usuarioAdminService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private IAlimentoService alimentoService;
	
	
	//OBTENER TODOS LOS USUARIOS
	@Operation(summary = "Listar usuarios", description = "Obtiene la lista de todos los usuarios registrados en el sistema.")
	@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios recuperados correctamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioAdminListaDto.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	@GetMapping("/usuarios")
	public ResponseEntity<List<UsuarioAdminListaDto>> obtenerUsuarios() {
		
		List<UsuarioAdminListaDto> listaUsuarios = usuarioAdminService.obtenerUsuarios();
		
		return ResponseEntity.ok(listaUsuarios);
	}
	
	
	// FILTRO POR EMAIL
	@Operation(summary = "Obtener usuario por email", 
             description = "Recupera usuarios que coincidan con el email proporcionado.")
	@ApiResponses({
	      @ApiResponse(responseCode = "200", description = "Usuario encontrado", 
	                   content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
	      @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
	      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	  })
	@GetMapping("/usuarios/{email}")
	public ResponseEntity<?> getUsuariosByEmail(@Parameter(description = "Email del usuario a consultar", required = true)
			@PathVariable String email) {
        try {
            List<Usuario> usuarios = usuarioAdminService.findByEmail(email);
            
            if (usuarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron usuarios con el email: " + email);
            }
            
            // Convertir entidades a DTOs (opcional pero recomendado)
            List<UsuarioResponseDto> response = usuarios.stream()
                    .map(usuario -> new UsuarioResponseDto(
                            usuario.getNombre(),
                            usuario.getPassword(),
                            usuario.getEmail(),
                            usuario.getSexo(),
                            usuario.getEdad(),
                            usuario.getAltura(),
                            usuario.getFechaRegistro()))
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al buscar usuarios: " + e.getMessage());
        }
    }
	
	
	// FILTRO POR SEXO --> La Url sería: 'localhost:XXXX/admin/usuarios/sexo/hombre', por ejemplo.
	@Operation(summary = "Filtrar usuarios por sexo", 
            description = "Devuelve una lista de usuarios según el sexo especificado (HOMBRE o MUJER).")
	@ApiResponses({
	     @ApiResponse(responseCode = "200", description = "Usuarios filtrados correctamente", 
	                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
	     @ApiResponse(responseCode = "400", description = "Sexo inválido"),
	     @ApiResponse(responseCode = "404", description = "No se encontraron usuarios para el sexo dado"),
	     @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@GetMapping("/usuarios/sexo/{sexo}")
	public ResponseEntity<?> getUsuariosBySexo(@Parameter(description = "Sexo del usuario a consultar", required = true)
			@PathVariable String sexo) {
	    try {
	        // Convertir el String a Enum (case-insensitive)
	        Sexo sexoEnum = Sexo.valueOf(sexo.toUpperCase());
	        
	        List<Usuario> usuarios = usuarioAdminService.findBySexo(sexoEnum);
	        
	        if (usuarios.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("No se encontraron usuarios con el sexo: " + sexo);
	        }
	        
	        // Convertir a DTO
	        List<UsuarioResponseDto> response = usuarios.stream()
	                .map(usuario -> UsuarioResponseDto.builder()
	                        .nombre(usuario.getNombre())
	                        .email(usuario.getEmail())
	                        .sexo(usuario.getSexo())
	                        .edad(usuario.getEdad())
	                        .altura(usuario.getAltura())
	                        .fechaRegistro(usuario.getFechaRegistro())
	                        .build())
	                .collect(Collectors.toList());
	        
	        return ResponseEntity.ok(response);
	        
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest()
	                .body("Valor de sexo inválido. Opciones válidas: " + 
	                      Arrays.toString(Sexo.values()));
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al buscar usuarios: " + e.getMessage());
	    }
	}
	
	// FILTRO POR EDAD --> La Url sería: 'localhost:XXXX/admin/usuarios/edad/25', por ejemplo.
	@Operation(summary = "Filtrar usuarios por edad", 
	            description = "Devuelve una lista de usuarios que tengan la edad especificada.")
	 @ApiResponses({
	     @ApiResponse(responseCode = "200", description = "Usuarios filtrados correctamente", 
	                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
	     @ApiResponse(responseCode = "400", description = "Edad inválida"),
	     @ApiResponse(responseCode = "404", description = "No se encontraron usuarios con esa edad"),
	     @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	 })
	@GetMapping("/usuarios/edad/{edad}")
	public ResponseEntity<?> getUsuariosByEdad(@Parameter(description = "Edad del usuario a consultar", required = true)
												@PathVariable int edad) {
	    try {
	        // Validación básica de edad
	        if(edad < 0) {
	            return ResponseEntity.badRequest()
	                    .body("La edad no puede ser un valor negativo");
	        }
	        
	        List<Usuario> usuarios = usuarioAdminService.findByEdad(edad);
	        
	        if(usuarios.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("No se encontraron usuarios con edad: " + edad);
	        }
	        
	        // Conversión a DTO
	        List<UsuarioResponseDto> response = usuarios.stream()
	                .map(usuario -> UsuarioResponseDto.builder()
	                        .nombre(usuario.getNombre())
	                        .email(usuario.getEmail())
	                        .sexo(usuario.getSexo())
	                        .edad(usuario.getEdad())
	                        .altura(usuario.getAltura())
	                        .fechaRegistro(usuario.getFechaRegistro())
	                        .build())
	                .collect(Collectors.toList());
	        
	        return ResponseEntity.ok(response);
	        
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al buscar usuarios: " + e.getMessage());
	    }
	}
	
	@Operation(summary = "Suspender o reactivar un usuario", 
            description = "Cambia el estado de suspensión de un usuario en base a su email.")
	 @ApiResponses({
	     @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
	     @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
	     @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	 })
	@PutMapping("/usuarios/{email}/suspender")
	public ResponseEntity<?> cambiarEstadoUsuario(
	        @Parameter(description = "Email del usuario a suspender/reactivar", required = true)
	        @PathVariable String email) {

	    try {
	        UsuarioAdminListaDto usuarioActualizado = usuarioAdminService.cambiarEstadoUsuario(email);
	        
	        String estado = usuarioActualizado.isSuspendida() ? "suspendida" : "reactivada";

	        return ResponseEntity.ok(Map.of(
	            "mensaje", "Cuenta " + estado + " exitosamente",
	            "usuario", usuarioActualizado
	        ));
	        
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body(Map.of("error", e.getMessage()));

	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	            .body(Map.of("error", "Error al actualizar el estado: " + e.getMessage()));
	    }
	}
	
	
	
	
	// Método para crear alimentos
	@Operation(summary = "Crear un nuevo alimento", 
              description = "Agrega un nuevo alimento en el sistema.")
	   @ApiResponses({
	       @ApiResponse(responseCode = "201", description = "Alimento creado exitosamente"),
	       @ApiResponse(responseCode = "400", description = "Datos inválidos"),
	       @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
	       @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	   })
	@PostMapping("/alimentos/crear")
	public ResponseEntity<?> createAlimento(@Parameter(description = "Alimento a crear", required = true)
			@RequestBody Alimento alimento) {
	    try {
	        Alimento nuevoAlimento = alimentoService.crearAlimento(alimento);
	        
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(Map.of(
	                    "mensaje", "Alimento creado exitosamente",
	                    "alimento", Map.of(
	                        "id", nuevoAlimento.getIdAlimento(),
	                        "nombre", nuevoAlimento.getNombre(),
	                        "marca", nuevoAlimento.getMarca(),
	                        "kcal", nuevoAlimento.getKcal(),
	                        "categoria", Map.of(
	                            "id", nuevoAlimento.getCategoria().getIdCategoria(),
	                            "nombre", nuevoAlimento.getCategoria().getNombre()
	                        )
	                    )
	                ));
	                
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al crear el alimento: " + e.getMessage());
	    }
	}
	
	
	// Método para modificar alimentos
	 @Operation(summary = "Modificar un alimento existente", 
             description = "Actualiza los datos de un alimento identificado por su ID.")
	  @ApiResponses({
	      @ApiResponse(responseCode = "200", description = "Alimento actualizado correctamente"),
	      @ApiResponse(responseCode = "400", description = "Datos inválidos"),
	      @ApiResponse(responseCode = "404", description = "Alimento no encontrado"),
	      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	  })
	@PutMapping("/alimentos/modificar/{idAlimento}")
	public ResponseEntity<?> modificarAlimento(@Parameter(description = "ID de alimento a modificar", required = true)
												@PathVariable int idAlimento,
												@Parameter(description = "Datos actualizados del alimento", required = true)
			        							@RequestBody Alimento alimentoActualizado) {
		
	    try {
	        Alimento alimentoModificado = alimentoService.modificarAlimento(idAlimento, alimentoActualizado);
	        
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Alimento modificado exitosamente",
	                    "alimento", Map.of(
	                        "id", alimentoModificado.getIdAlimento(),
	                        "nombre", alimentoModificado.getNombre(),
	                        "marca", alimentoModificado.getMarca(),
	                        "kcal", alimentoModificado.getKcal(),
	                        "categoria", Map.of(
	                            "id", alimentoModificado.getCategoria().getIdCategoria(),
	                            "nombre", alimentoModificado.getCategoria().getNombre()
	                        )

	                    )
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al modificar el alimento: " + e.getMessage());
	    }
	}
	
	 @Operation(summary = "Obtener resumen de usuarios", 
             description = "Devuelve estadísticas globales para el dashboard administrativo.")
	  @ApiResponses({
	      @ApiResponse(responseCode = "200", description = "Resumen obtenido correctamente", 
	                   content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResumenDto.class))),
	      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	  })
	@GetMapping("/dashboard/resumen")
	public ResponseEntity<?> getResumenUsuarios() {
	    try {
	        UserResumenDto resumen = usuarioAdminService.obtenerResumenUsuarios();
	        return ResponseEntity.ok(resumen);
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al obtener el resumen de usuarios: " + e.getMessage());
	    }
	}

	
	
}
