package easyfit.restcontroller;

import java.util.Map;
import java.util.NoSuchElementException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import easyfit.models.dtos.ActualizarActividadRequestDto;
import easyfit.models.dtos.ActualizarObjetivoRequestDto;
import easyfit.models.dtos.ActualizarPesoRequestDto;
import easyfit.models.dtos.MacrosRequestDto;
import easyfit.models.dtos.ObjetivoResponseDto;
import easyfit.models.dtos.ValorNutriconalResponseDto;
import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.repositories.IObjetivoRepository;
import easyfit.services.ValorNutricionalService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/misobjetivos")
@CrossOrigin(origins = "*")
public class ObjetivosRestController {

	@Autowired
	private IObjetivoRepository objetivoService;
	
	@Autowired 
	private ModelMapper mapper;
	
	//	- **GET /usuarios/misobjetivos** — Obtener todos los objetivos del usuario. Sí
	//	- **PUT /usuarios/misobjetivos/macros** — Cambiar macros de mis objetivos. Sí
	//	- **PUT /usuarios/misobjetivos/cambiarPesoActual** — Cambiar el peso actual. Sí
	//	- **PUT /usuarios/misobjetivos/cambiarPesoObjetivo** — Cambiar el peso objetivo. Sí
	//	- **PUT /usuarios/misobjetivos/nivelactividad** — Cambiar nivel de actividad física. Sí
	//	- **PUT /usuarios/misobjetivos/metasemanal** — Ajustar ritmo semanal de cambio de peso. Sí
	//	- **PUT /usuarios/misobjetivos/metaobjetivo** — Establecer objetivo general (perder, mantener, ganar).

	//RUTA CON METODO PARA OBTENER TODOS LOS OBJETIVOS DEL USUARIO
	@GetMapping("/")
	public ResponseEntity<ObjetivoResponseDto> objetivosUsuario() {
		//Obtenemos el usuario autenticado
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		//Mapeamos los objetivos del usuario en una respuesta dto
		ObjetivoResponseDto respuestaDto = mapper.map(usuario.getObjetivo(),ObjetivoResponseDto.class);
		
		return ResponseEntity.ok(respuestaDto);	
	}

	//RUTA CON METODO PARA OBTENER TODOS LOS OBJETIVOS DEL USUARIO
	@PutMapping("/macros")
	public ResponseEntity<ValorNutriconalResponseDto> modificarMacros(@RequestBody @Valid MacrosRequestDto macrosDto) {
		
		//Obtenemos el usuario autenticado
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		//Actualizamos los macronutrientes y guardamos la respuesta
		ValorNutriconalResponseDto  respuestaDto =  objetivoService.actualizarMacrosPorPorcentaje(usuario, macrosDto);
		
		return ResponseEntity.ok(respuestaDto);	
	}
	
	@PutMapping("/cambiarPesoActual")
	public ResponseEntity<?> actualizarPesoActual(
	        @RequestBody ActualizarPesoRequestDto request,
	        @AuthenticationPrincipal Usuario usuario) {
		
	    try {
	        Objetivo objetivoActualizado = objetivoService.actualizarPesoActual(request.getCampoPeso(), usuario);
	        
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Peso actual actualizado exitosamente",
	                    "nuevoPeso", objetivoActualizado.getPesoActual(),
	                    "pesoObjetivo", objetivoActualizado.getPesoObjetivo(),
	                    "objetivoUsuario", objetivoActualizado.getObjetivoUsuario()
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al actualizar el peso: " + e.getMessage());
	    }
	}
	
	@PutMapping("/cambiarPesoObjetivo")
	public ResponseEntity<?> actualizarPesoObjetivo(
	        @RequestBody ObjetivoResponseDto request,
	        @AuthenticationPrincipal Usuario usuario) {
	    try {
	        Objetivo objetivoActualizado = objetivoService.actualizarPesoObjetivo(request.getPesoObjetivo(), usuario);
	        
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Peso objetivo actualizado exitosamente",
	                    "pesoActual", objetivoActualizado.getPesoActual(),
	                    "nuevoPesoObjetivo", objetivoActualizado.getPesoObjetivo(),
	                    "objetivoUsuario", objetivoActualizado.getObjetivoUsuario().toString()
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al actualizar el peso objetivo: " + e.getMessage());
	    }
	}
	
	
	@PutMapping("/nivelactividad")
	public ResponseEntity<?> actualizarNivelActividad(
	        @RequestBody ActualizarActividadRequestDto request,
	        @AuthenticationPrincipal Usuario usuario) {
	    try {
	        Objetivo objetivoActualizado = objetivoService.actualizarActividad(request.getActividad(), usuario);
	        
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Nivel de actividad actualizado exitosamente",
	                    "nuevaActividad", objetivoActualizado.getActividad().toString(),
	                    "objetivoUsuario", objetivoActualizado.getObjetivoUsuario().toString()
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al actualizar el nivel de actividad: " + e.getMessage());
	    }
	}
	
	@PutMapping("/metasemanal")
	public ResponseEntity<?> actualizarMetaSemanal(
	        @RequestBody ActualizarPesoRequestDto request,
	        @AuthenticationPrincipal Usuario usuario) {
	    try {
	        Objetivo objetivoActualizado = objetivoService.actualizarOpcionPeso(request.getOpcionPeso(), usuario);
	        
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Meta semanal actualizada exitosamente",
	                    "nuevaOpcionPeso", objetivoActualizado.getOpcionPeso().toString(),
	                    "objetivoUsuario", objetivoActualizado.getObjetivoUsuario().toString()
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al actualizar la meta semanal: " + e.getMessage());
	    }
	}
	
	@PutMapping("/metaobjetivo")
	public ResponseEntity<?> actualizarObjetivoGeneral(
	        @RequestBody ActualizarObjetivoRequestDto request,
	        @AuthenticationPrincipal Usuario usuario) {
	    try {
	        Objetivo objetivoActualizado = objetivoService.actualizarObjetivoUsuario(request.getObjetivoUsuario(), usuario);
	        
	        return ResponseEntity.ok()
	                .body(Map.of(
	                    "mensaje", "Objetivo actualizado exitosamente",
	                    "nuevoObjetivo", objetivoActualizado.getObjetivoUsuario().toString(),
	                    "pesoActual", objetivoActualizado.getPesoActual(),
	                    "pesoObjetivo", objetivoActualizado.getPesoObjetivo()
	                ));
	                
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Error al actualizar el objetivo: " + e.getMessage());
	    }
	}
}
