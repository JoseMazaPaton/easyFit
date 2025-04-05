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

import easyfit.models.dtos.auth.UsuarioResponseDto;
import easyfit.models.entities.Alimento;
import easyfit.models.entities.Categoria;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Sexo;
import easyfit.services.IAlimentoService;
import easyfit.services.ICategoriaService;
import easyfit.services.IUsuarioAdminService;


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminRestController {
	
	@Autowired
	private IUsuarioAdminService usuarioAdminService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private IAlimentoService alimentoService;
	
	
	
	// FILTRO POR EMAIL
	@GetMapping("/usuarios/{email}")
	public ResponseEntity<?> getUsuariosByEmail(@PathVariable String email) {
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
	@GetMapping("/usuarios/sexo/{sexo}")
	public ResponseEntity<?> getUsuariosBySexo(@PathVariable String sexo) {
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
	@GetMapping("/usuarios/edad/{edad}")
	public ResponseEntity<?> getUsuariosByEdad(@PathVariable int edad) {
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
	
	
	// Suspensión o reactivación de cuentas de usuario por email. 
	@PutMapping("/usuarios/{email}/suspender")
	public ResponseEntity<?> toggleSuspension(@PathVariable String email) {
	    try {
	        Usuario usuarioActualizado = usuarioAdminService.toggleSuspension(email);
	        
	        UsuarioResponseDto response = UsuarioResponseDto.builder()
	            .nombre(usuarioActualizado.getNombre())
	            .email(usuarioActualizado.getEmail())
	            .sexo(usuarioActualizado.getSexo())
	            .edad(usuarioActualizado.getEdad())
	            .altura(usuarioActualizado.getAltura())
	            .fechaRegistro(usuarioActualizado.getFechaRegistro())
	            .build();
	        
	        String estado = usuarioActualizado.isSuspendida() ? "suspendida" : "reactivada";
	        return ResponseEntity.ok()
	            .body(Map.of(
	                "mensaje", "Cuenta " + estado + " exitosamente",
	                "usuario", response
	            ));
	            
	    } catch (RuntimeException e) {
	    	
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body("Error: " + e.getMessage());
	        
	    } catch (Exception e) {
	    	
	        return ResponseEntity.internalServerError()
	            .body("Error al actualizar el estado: " + e.getMessage());
	    }
	}
	
	
	// Método para crear categorías
	@PostMapping("/categorias/crear")
	public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
	    try {
	        Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
	        
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(Map.of(
	                    "mensaje", "Categoría creada exitosamente",
	                    "categoria", Map.of(
	                        "id", nuevaCategoria.getIdCategoria(),
	                        "nombre", nuevaCategoria.getNombre()
	                    )
	                ));
	                
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al crear la categoría: " + e.getMessage());
	    }
	}
	
	// Método para modificar categorías
	@PutMapping("/categorias/modificar/{idCategoria}")
	public ResponseEntity<?> modificarCategoria(@PathVariable int idCategoria,
	        									@RequestBody Categoria categoria) {
	    try {
	        Categoria categoriaActualizada = categoriaService.modificarCategoria(idCategoria, categoria);
	        
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Categoría actualizada exitosamente",
	                    "categoria",
	                    Map.of(
		                    "id", categoriaActualizada.getIdCategoria(),
		                    "nombre", categoriaActualizada.getNombre()
	                    	)
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(e.getMessage());
	        
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest()
	                .body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al actualizar la categoría: " + e.getMessage());
	    }
	}
	
	// Método para eliminar categorías
	@DeleteMapping("/categorias/eliminar/{idCategoria}")
	public ResponseEntity<?> eliminarCategoria(@PathVariable int idCategoria) {
	    try {
	        categoriaService.eliminarCategoria(idCategoria);
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Categoría eliminada exitosamente",
	                    "idEliminado", idCategoria
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al eliminar la categoría: " + e.getMessage());
	    }
	}
	
	// Método para crear alimentos
	@PostMapping("/alimentos/crear")
	public ResponseEntity<?> createAlimento(@RequestBody Alimento alimento) {
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
	@PutMapping("/alimentos/modificar/{idAlimento}")
	public ResponseEntity<?> modificarAlimento(@PathVariable int idAlimento,
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
	
	
	// Método para eliminar alimentos
	@DeleteMapping("/alimentos/eliminar/{idAlimento}")
	public ResponseEntity<?> eliminarAlimento(@PathVariable int idAlimento) {
	    try {
	        alimentoService.eliminarAlimento(idAlimento);
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Alimento eliminado exitosamente",
	                    "idEliminado", idAlimento
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al eliminar el alimento: " + e.getMessage());
	    }
	}
	
	
	
}
