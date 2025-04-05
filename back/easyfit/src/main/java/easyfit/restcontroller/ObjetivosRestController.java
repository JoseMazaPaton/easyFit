package easyfit.restcontroller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import easyfit.models.dtos.objetivo.ObjetivoResponseDto;
import easyfit.models.dtos.objetivo.PesoActualDto;
import easyfit.models.dtos.valornutricional.MacrosRequestDto;
import easyfit.models.dtos.valornutricional.ValorNutriconalResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.services.IObjetivoService;
import easyfit.services.IValorNutricionalService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/objetivos")
@CrossOrigin(origins = "*")
public class ObjetivosRestController {

	@Autowired
	private IObjetivoService objetivoService;
	
	@Autowired
	private IValorNutricionalService valorNutricionalService;
	
	@Autowired 
	private ModelMapper mapper;
	



//	- **GET /objetivos/misobjetivos** — Obtener todos los objetivos actuales del usuario (kcal, macros, peso, objetivo, etc.).
//	- **PUT /objetivos/macros** — Cambiar macros de los objetivos (proteínas, carbohidratos, grasas).
//	- **PUT /objetivos/pesoactual** — Cambiar el peso objetivo.
//	- **PUT /objetivos/pesometa** — Cambiar el peso objetivo.
//	- **PUT /objetivos/metaobjetivo** — Establecer objetivo general (perder, mantener, ganar).
//	- **PUT /objetivos/nivelactividad** — Cambiar nivel de actividad física.
//	- **PUT /objetivos/metasemanal** — Ajustar ritmo semanal de cambio de peso.



	//RUTA CON METODO PARA OBTENER TODOS LOS OBJETIVOS DEL USUARIO
	@GetMapping("/misobjetivos")
	public ResponseEntity<ObjetivoResponseDto> objetivosUsuario() {
		//Obtenemos el usuario autenticado
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		//Mapeamos los objetivos del usuario en una respuesta dto
		ObjetivoResponseDto respuestaDto = mapper.map(usuario.getObjetivo(),ObjetivoResponseDto.class);
		
		//Le añadimos los valores nutricionales a la respuesta mapeados
		respuestaDto.setValores(mapper.map(usuario.getValorNutricional(), ValorNutriconalResponseDto.class));;
		
		return ResponseEntity.ok(respuestaDto);	
	}

	//RUTA CON METODO PARA MODIFICAR LOS MACROS DEL USUARIO POR PORCENTAJE
	@PutMapping("/macros")
	public ResponseEntity<ValorNutriconalResponseDto> modificarMacros(@RequestBody @Valid MacrosRequestDto macrosDto) {
		
		//Obtenemos el usuario autenticado
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		//Actualizamos los macronutrientes y guardamos la respuesta
		ValorNutriconalResponseDto  respuestaDto =  valorNutricionalService.actualizarMacrosPorPorcentaje(usuario, macrosDto);
		
		return ResponseEntity.ok(respuestaDto);	
	}
	
	
	//REGISTRO DE NUEVO PESO
	@PutMapping("/pesoactual")
	public ResponseEntity<ObjetivoResponseDto> actualizarPesoActual(@RequestBody @Valid PesoActualDto pesoDto) {
		
		//Obtenemos el usuario autenticado
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		//Actualizamos el peso con metodo del service
		ObjetivoResponseDto respuestaDto = objetivoService.actualizarPesoActual(pesoDto, usuario);
			
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	//REGISTRO DE NUEVO PESO
	@PutMapping("/pesometa")
	public ResponseEntity<ObjetivoResponseDto> actualizarPesoObjetivo(@RequestBody @Valid PesoActualDto pesoDto) {
		
		//Obtenemos el usuario autenticado
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		//Actualizamos el peso con metodo del service
		ObjetivoResponseDto respuestaDto = objetivoService.actualizarPesoActual(pesoDto, usuario);
			
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	
	
//	@PutMapping("/cambiarPesoObjetivo")
//	public ResponseEntity<?> actualizarPesoObjetivo(
//	        @RequestBody ObjetivoResponseDto request,
//	        @AuthenticationPrincipal Usuario usuario) {
//	    try {
//	        Objetivo objetivoActualizado = objetivoService.actualizarPesoObjetivo(request.getPesoObjetivo(), usuario);
//	        
//	        return ResponseEntity.ok()
//	                .body(Map.of(
//	                    "mensaje", "Peso objetivo actualizado exitosamente",
//	                    "pesoActual", objetivoActualizado.getPesoActual(),
//	                    "nuevoPesoObjetivo", objetivoActualizado.getPesoObjetivo(),
//	                    "objetivoUsuario", objetivoActualizado.getObjetivoUsuario().toString()
//	                ));
//	                
//	    } catch (NoSuchElementException e) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//	    } catch (IllegalArgumentException e) {
//	        return ResponseEntity.badRequest().body(e.getMessage());
//	    } catch (Exception e) {
//	        return ResponseEntity.internalServerError()
//	                .body("Error al actualizar el peso objetivo: " + e.getMessage());
//	    }
//	}
//	
//	
//	@PutMapping("/nivelactividad")
//	public ResponseEntity<?> actualizarNivelActividad(
//	        @RequestBody ActualizarActividadRequestDto request,
//	        @AuthenticationPrincipal Usuario usuario) {
//	    try {
//	        Objetivo objetivoActualizado = objetivoService.actualizarActividad(request.getActividad(), usuario);
//	        
//	        return ResponseEntity.ok()
//	                .body(Map.of(
//	                    "mensaje", "Nivel de actividad actualizado exitosamente",
//	                    "nuevaActividad", objetivoActualizado.getActividad().toString(),
//	                    "objetivoUsuario", objetivoActualizado.getObjetivoUsuario().toString()
//	                ));
//	                
//	    } catch (NoSuchElementException e) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//	    } catch (IllegalArgumentException e) {
//	        return ResponseEntity.badRequest().body(e.getMessage());
//	    } catch (Exception e) {
//	        return ResponseEntity.internalServerError()
//	                .body("Error al actualizar el nivel de actividad: " + e.getMessage());
//	    }
//	}
//	
//	@PutMapping("/metasemanal")
//	public ResponseEntity<?> actualizarMetaSemanal(
//	        @RequestBody ActualizarPesoRequestDto request,
//	        @AuthenticationPrincipal Usuario usuario) {
//	    try {
//	        Objetivo objetivoActualizado = objetivoService.actualizarOpcionPeso(request.getOpcionPeso(), usuario);
//	        
//	        return ResponseEntity.ok()
//	                .body(Map.of(
//	                    "mensaje", "Meta semanal actualizada exitosamente",
//	                    "nuevaOpcionPeso", objetivoActualizado.getOpcionPeso().toString(),
//	                    "objetivoUsuario", objetivoActualizado.getObjetivoUsuario().toString()
//	                ));
//	                
//	    } catch (NoSuchElementException e) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//	    } catch (IllegalArgumentException e) {
//	        return ResponseEntity.badRequest().body(e.getMessage());
//	    } catch (Exception e) {
//	        return ResponseEntity.internalServerError()
//	                .body("Error al actualizar la meta semanal: " + e.getMessage());
//	    }
//	}
//	
//	@PutMapping("/metaobjetivo")
//	public ResponseEntity<?> actualizarObjetivoGeneral(
//	        @RequestBody ActualizarObjetivoRequestDto request,
//	        @AuthenticationPrincipal Usuario usuario) {
//	    try {
//	        Objetivo objetivoActualizado = objetivoService.actualizarObjetivoUsuario(request.getObjetivoUsuario(), usuario);
//	        
//	        return ResponseEntity.ok()
//	                .body(Map.of(
//	                    "mensaje", "Objetivo actualizado exitosamente",
//	                    "nuevoObjetivo", objetivoActualizado.getObjetivoUsuario().toString(),
//	                    "pesoActual", objetivoActualizado.getPesoActual(),
//	                    "pesoObjetivo", objetivoActualizado.getPesoObjetivo()
//	                ));
//	                
//	    } catch (NoSuchElementException e) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//	    } catch (IllegalArgumentException e) {
//	        return ResponseEntity.badRequest().body(e.getMessage());
//	    } catch (Exception e) {
//	        return ResponseEntity.internalServerError()
//	                .body("Error al actualizar el objetivo: " + e.getMessage());
//	    }
//	}
}
