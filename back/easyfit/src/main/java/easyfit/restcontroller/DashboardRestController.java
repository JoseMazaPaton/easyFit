package easyfit.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import easyfit.models.dtos.consumodiario.DiarioResponseDto;
import easyfit.models.dtos.consumodiario.HistorialCaloriasDto;
import easyfit.models.dtos.consumodiario.HistorialPesoDto;
import easyfit.models.entities.Usuario;
import easyfit.services.IConsumoDiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Operaciones relacionadas con el Dashboard de Easyfit.")
@CrossOrigin(origins = "*")
public class DashboardRestController {

	@Autowired
	private IConsumoDiarioService diarioService;
	
	//METODO CON RUTA RESUMEN DE KCAL DEL DIA DEL USUARIO
	@GetMapping("/resumendiario")
	
	@Operation(summary = "Obtener resumen diario de Usuario", 
    description = "Obtiene un resumen del Usuario logueado con datos como calorías consumidas, quemadas, netas, etc.")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Resumen diario obtenido correctamente",
						content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiarioResponseDto.class))),
				@ApiResponse(responseCode = "500", description = "Error al obtener el resumen diario",
				content = @Content)
		})
	public ResponseEntity<DiarioResponseDto> resumenDiarioUsuario (@AuthenticationPrincipal Usuario usuaurio) {
		
		DiarioResponseDto respuestaDto =  diarioService.obtenerResumenDiario(usuaurio);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	//METODO QUE OBJETIENE RESUMEN DE PESO ACUTUAL, PESO OBJETIVO , PESO INICIAL - PARA GRAFICO CIRCULAR EN EL FRONT	
	@GetMapping("/resumenpesos")
	@Operation(summary = "Obtener historial de peso", 
    description = "Obtiene un resumen de peso actual, peso objetivo y peso inicial del usuario autenticado.")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Resumen de peso obtenido correctamente",
						content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistorialPesoDto.class))),
				@ApiResponse(responseCode = "500", description = "Error al obtener el historial de peso",
				content = @Content)
	})
	public ResponseEntity<HistorialPesoDto> resumenPesosUsuario(@AuthenticationPrincipal Usuario usuario) {
		
		HistorialPesoDto respuestaDto = diarioService.resumenPesosUsuario(usuario);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	@GetMapping("/resumencalorias")
	@Operation(summary = "Obtener resumen de calorías", 
    description = "Obtiene las calorías consumidas por el usuario durante los últimos 7 días.")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Resumen de calorías obtenido correctamente",
						content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistorialCaloriasDto.class))),
				@ApiResponse(responseCode = "500", description = "Error al obtener el historial de calorías",
				content = @Content)
	})
	public ResponseEntity<List<HistorialCaloriasDto>> resumenCalorias7dias (@AuthenticationPrincipal Usuario usuario) {
		
		List<HistorialCaloriasDto> respuestaDto = diarioService.getConsumoUltimos7Dias(usuario);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
}
