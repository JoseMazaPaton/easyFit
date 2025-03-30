package easyfit.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import easyfit.models.entities.Progreso;
import easyfit.models.entities.Usuario;
import easyfit.services.IProgresoService;

@RestController
@RequestMapping("/progreso")
@CrossOrigin(origins = "*")
public class ProgresoRestController {
	
	
	@Autowired
	private IProgresoService progresoService;

	 @GetMapping("/pesoActual-Objetivo")
	    public ResponseEntity<?> getProgresoPeso(@AuthenticationPrincipal Usuario usuario) {
	        try {
	            Map<String, Double> progreso = progresoService.obtenerProgresoPeso(usuario.getEmail());
	            
	            if(progreso.isEmpty()) {
	                return ResponseEntity.noContent().build();
	            }
	            
	            return ResponseEntity.ok(progreso);
	            
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(404).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError()
	                    .body("Error al obtener el progreso: " + e.getMessage());
	        }
	    }
	 
	 @PostMapping("/nuevoPeso")
	    public ResponseEntity<?> registrarNuevoPeso(@RequestBody Map<String, Double> request,
	            									@AuthenticationPrincipal Usuario usuario) {
	        
		 try {
	            Double peso = request.get("peso");
	            
	            if(peso == null) {
	                return ResponseEntity.badRequest().body("El campo 'peso' es obligatorio");
	            }
	            
	            Progreso progreso = progresoService.registrarNuevoPeso(peso, usuario);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("mensaje", "Peso registrado exitosamente");
	            response.put("idRegistro", progreso.getIdProgreso());
	            response.put("peso", progreso.getPeso());
	            response.put("fechaRegistro", java.time.LocalDateTime.now()); // Si tuvieras el campo fecha en la entidad
	            
	            return ResponseEntity.status(201).body(response);
	            
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	            
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError()
	                    .body("Error al registrar el peso: " + e.getMessage());
	        }
	    }
}
