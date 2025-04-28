package easyfit.restcontroller;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import easyfit.models.dtos.alimentos.ActualizarCantidadAlimentoRequestDto;
import easyfit.models.dtos.alimentos.AgregarAlimentoRequestDto;
import easyfit.models.dtos.comida.ComidaDiariaDto;
import easyfit.models.dtos.comida.ResumenComidaDto;
import easyfit.models.entities.Comida;
import easyfit.models.entities.Usuario;
import easyfit.services.IComidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/comidas")
@Tag(name = "Comidas", description = "Operaciones relacionadas con los comidas de Easyfit.")
@CrossOrigin(origins = "*")
public class ComidaRestController {
	
	@Autowired
	private IComidaService comidaService;
	
	
	@Operation(summary = "Obtener comidas del día", description = "Recupera las comidas registradas para una fecha dada por el usuario autenticado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comidas del día obtenidas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComidaDiariaDto.class))),
        @ApiResponse(responseCode = "204", description = "No hay comidas para la fecha indicada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	@GetMapping("/fecha")
	public ResponseEntity<?> getComidasDelDia(@Parameter(description = "Fecha de la comida a consultar (YYYY-MM-DD)", required = true)
    											@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            									@AuthenticationPrincipal Usuario usuario) {
        try {
            List<ComidaDiariaDto> comidas = comidaService.obtenerComidasDelDia(fecha, usuario.getEmail());
            
            if(comidas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(comidas);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener las comidas: " + e.getMessage());
        }
    }

	@Operation(summary = "Crear comida", description = "Registra una nueva comida para el usuario autenticado.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Comida creada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comida.class))),
        @ApiResponse(responseCode = "400", description = "Datos de comida inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	@PostMapping("/crear")
	public ResponseEntity<?> crearComida(
			@Parameter(description = "Objeto Comida a crear", required = true)
            @RequestBody Comida comida,
            @AuthenticationPrincipal Usuario usuario) {
        try {
            // Asignar usuario autenticado
            comida.setUsuario(usuario);
            
            Comida nuevaComida = comidaService.crearComida(comida);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Comida creada exitosamente");
            response.put("idComida", nuevaComida.getIdComida());
            response.put("nombre", nuevaComida.getNombre());
            response.put("orden", nuevaComida.getOrden());
            response.put("fecha", nuevaComida.getFecha());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear la comida: " + e.getMessage());
        }
    }
	
	@Operation(summary = "Añadir alimento a comida", description = "Agrega un alimento existente a una comida específica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Alimento agregado",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Comida o alimento no encontrado"),
        @ApiResponse(responseCode = "400", description = "Cantidad inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	@PostMapping("/{idComida}/añadirAlimento")
	public ResponseEntity<?> agregarAlimentoAComida(@Parameter(description = "ID de la comida a la que se va a añadir el Alimento.", required = true)
													@PathVariable int idComida,
													@Parameter(description = "Datos del alimento a agregar (id y cantidad)", required = true)
											        @RequestBody AgregarAlimentoRequestDto request,
											        @AuthenticationPrincipal Usuario usuario) {
	    
		try {
	        comidaService.agregarAlimentoAComida(idComida, request.getIdAlimento(), request.getCantidad());
	        
	        return ResponseEntity.ok().body(Map.of(
	            "mensaje", "Alimento agregado a la comida exitosamente",
	            "idComida", idComida,
	            "idAlimento", request.getIdAlimento()
	        ));
	        
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al agregar alimento: " + e.getMessage());
	    }
	}
	
	
	@Operation(summary = "Eliminar alimento de comida", description = "Elimina un alimento de una comida existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Alimento eliminado",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Comida o alimento no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	@DeleteMapping("/{idComida}/alimentos/{idAlimento}")
	public ResponseEntity<?> eliminarAlimentoDeComida(@Parameter(description = "ID de la comida a la que se va a eliminar el Alimento.", required = true)
														@PathVariable int idComida,
														@Parameter(description = "ID del alimento que se va eliminar de la comida.", required = true)
												        @PathVariable int idAlimento,
												        @AuthenticationPrincipal Usuario usuario) {
	    
		try {
	        comidaService.eliminarAlimentoDeComida(idComida, idAlimento);
	        
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Alimento eliminado de la comida exitosamente",
	                    "idComida", idComida,
	                    "idAlimento", idAlimento
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al eliminar el alimento: " + e.getMessage());
	    }
	}
	
	
	@Operation(summary = "Actualizar cantidad de alimento", description = "Modifica la cantidad de un alimento en una comida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cantidad actualizada",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Comida o alimento no encontrado"),
        @ApiResponse(responseCode = "400", description = "Cantidad inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	@PutMapping("/{idComida}/alimentos/{idAlimento}")
	public ResponseEntity<?> actualizarCantidadAlimento(@Parameter(description = "ID de la comida a la que se va a modificar el Alimento.", required = true)
														@PathVariable int idComida,
														@Parameter(description = "ID del Alimento que se va modificar de la Comida.", required = true)
												        @PathVariable int idAlimento,
												        @Parameter(description = "Nueva cantidad a asignar", required = true)
												        @RequestBody ActualizarCantidadAlimentoRequestDto request,
												        @AuthenticationPrincipal Usuario usuario) {
	    try {
	        comidaService.actualizarCantidadAlimento(idComida, idAlimento, request.getCantidad());
	        
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Cantidad actualizada exitosamente",
	                    "idComida", idComida,
	                    "idAlimento", idAlimento,
	                    "nuevaCantidad", request.getCantidad()
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al actualizar la cantidad: " + e.getMessage());
	    }
	}
	
	// ComidaRestController.java
	@Operation(summary = "Eliminar comida", description = "Elimina una comida por su ID para el usuario autenticado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comida eliminada",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Comida no encontrada"),
        @ApiResponse(responseCode = "403", description = "Usuario no autorizado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	@DeleteMapping("/{idComida}")
	public ResponseEntity<?> eliminarComida(@Parameter(description = "ID de la comida que se va a eliminar.", required = true)
											@PathVariable int idComida,
	        								@AuthenticationPrincipal Usuario usuario) {
	    try {
	        comidaService.eliminarComida(idComida, usuario);
	        
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Comida eliminada exitosamente",
	                    "idComida", idComida,
	                    "fecha", LocalDate.now().toString()
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (SecurityException e) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al eliminar la comida: " + e.getMessage());
	    }
	}
	
	// ComidaRestController.java
	
	@Operation(summary = "Resumen de comida", description = "Obtiene un resumen nutricional de una comida por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resumen obtenido",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResumenComidaDto.class))),
        @ApiResponse(responseCode = "404", description = "Comida no encontrada"),
        @ApiResponse(responseCode = "403", description = "Usuario no autorizado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	@GetMapping("/{idComida}/resumen")
	public ResponseEntity<?> getResumenComida(@Parameter(description = "ID de la comida que se va a obtener el resumen.", required = true)
												@PathVariable int idComida,
	        									@AuthenticationPrincipal Usuario usuario) {
	    try {
	        ResumenComidaDto resumen = comidaService.obtenerResumenComida(idComida, usuario);
	        return ResponseEntity.ok(resumen);
	        
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (SecurityException e) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al obtener el resumen: " + e.getMessage());
	    }
	}
	
}
