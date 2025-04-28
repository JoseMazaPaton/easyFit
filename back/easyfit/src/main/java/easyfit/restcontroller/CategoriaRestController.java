package easyfit.restcontroller;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import easyfit.models.dtos.admin.CategoriasRecuento;
import easyfit.models.entities.Categoria;
import easyfit.services.ICategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorías", description = "Operaciones relacionadas con las categorías de Easyfit.")
@CrossOrigin(origins = "*")
public class CategoriaRestController {
	
	
	@Autowired
	private ICategoriaService categoriaService;

	@Operation(summary = "Listar todas las categorías", description = "Obtiene un listado de todas las categorías con su recuento de elementos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categorías recuperadas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriasRecuento.class))),
        @ApiResponse(responseCode = "204", description = "No hay categorías disponibles"),
        @ApiResponse(responseCode = "404", description = "No se encontraron categorías"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	@GetMapping("/todas")
	public ResponseEntity<?> listarTodasCategorias() {
	    try {
	        List<CategoriasRecuento> categorias = categoriaService.listarCategoriasConRecuento();

	        if (categorias.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }

	        return ResponseEntity.ok(categorias);

	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("No se encontraron categorías: " + e.getMessage());
	    } catch (DataAccessException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error en el acceso a datos: " + e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error inesperado al obtener las categorías: " + e.getMessage());
	    }
	}
	
	
// Método para crear categorías
	@Operation(summary = "Crear categoría", description = "Crea una nueva categoría en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Categoría creada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para la categoría"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	@PostMapping("/crear")
	public ResponseEntity<?> crearCategoria(@Parameter(description = "Categoria a crear", required = true)
			@RequestBody Categoria categoria) {
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
		@Operation(summary = "Modificar categoría", description = "Modifica una categoría existente por su ID.")
	    @ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Categoría actualizada",
	            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
	        @ApiResponse(responseCode = "400", description = "Datos inválidos para la modificación"),
	        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	    })
		@PutMapping("/modificar/{idCategoria}")
		public ResponseEntity<?> modificarCategoria(@Parameter(description = "ID de categoría a modificar", required = true)
													@PathVariable int idCategoria,
													@Parameter(description = "Objeto categoría con datos actualizados", required = true)
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
		@Operation(summary = "Eliminar categoría", description = "Elimina una categoría por su ID.")
	    	@ApiResponses({
	    		@ApiResponse(responseCode = "200", description = "Categoría eliminada",
	    				content = @Content(mediaType = "application/json")),
	        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	    })
		@DeleteMapping("/eliminar/{idCategoria}")
		public ResponseEntity<?> eliminarCategoria(@Parameter(description = "ID de categoría a eliminar", required = true)
													@PathVariable int idCategoria) {
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

}
