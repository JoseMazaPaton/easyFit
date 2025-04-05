package easyfit.restcontroller;

import java.time.LocalDate;
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
import easyfit.models.dtos.auth.ResumenComidaDto;
import easyfit.models.dtos.comida.ComidaDiariaDto;
import easyfit.models.entities.Comida;
import easyfit.models.entities.Usuario;
import easyfit.services.IComidaService;

@RestController
@RequestMapping("/comidas")
@CrossOrigin(origins = "*")
public class ComidaRestController {
	
	@Autowired
	private IComidaService comidaService;
	
	@GetMapping("/fecha")
    public ResponseEntity<?> getComidasDelDia(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
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

	@PostMapping("/crear")
    public ResponseEntity<?> crearComida(
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
	
	@PostMapping("/{idComida}/añadirAlimento")
	public ResponseEntity<?> agregarAlimentoAComida(@PathVariable int idComida,
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
	
	@DeleteMapping("/{idComida}/alimentos/{idAlimento}")
	public ResponseEntity<?> eliminarAlimentoDeComida(@PathVariable int idComida,
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
	
	@PutMapping("/{idComida}/alimentos/{idAlimento}")
	public ResponseEntity<?> actualizarCantidadAlimento(@PathVariable int idComida,
												        @PathVariable int idAlimento,
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
	@DeleteMapping("/{idComida}")
	public ResponseEntity<?> eliminarComida(@PathVariable int idComida,
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
	@GetMapping("/{idComida}/resumen")
	public ResponseEntity<?> getResumenComida(@PathVariable int idComida,
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
