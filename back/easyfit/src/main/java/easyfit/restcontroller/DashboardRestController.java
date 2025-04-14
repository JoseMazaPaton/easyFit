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
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Operaciones relacionadas con el Dashboard de Easyfit.")
@CrossOrigin(origins = "*")
public class DashboardRestController {

	@Autowired
	private IConsumoDiarioService diarioService;
	
	//METODO CON RUTA RESUMEN DE KCAL DEL DIA DEL USUARIO
	@GetMapping("/resumendiario")
	@Operation(summary = "Obtener resumen diario de Usuario", description = "Obtiene un resumen del Usuario logueado.")
	public ResponseEntity<DiarioResponseDto> resumenDiarioUsuario (@AuthenticationPrincipal Usuario usuaurio) {
		
		DiarioResponseDto respuestaDto =  diarioService.obtenerResumenDiario(usuaurio);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	//METODO QUE OBJETIENE RESUMEN DE PESO ACUTUAL, PESO OBJETIVO , PESO INICIAL - PARA GRAFICO CIRCULAR EN EL FRONT	
	@GetMapping("/resumenpesos")
	@Operation(summary = "Obtener historial de peso", description = "Obtiene un resumen del peso del usuario autenticado.")
	public ResponseEntity<HistorialPesoDto> resumenPesosUsuario(@AuthenticationPrincipal Usuario usuario) {
		
		HistorialPesoDto respuestaDto = diarioService.resumenPesosUsuario(usuario);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	@GetMapping("/resumencalorias")
	@Operation(summary = "Obtener resumen de calorías", description = "Obtiene un resumen de las calorías consumidas por el usuario logueado en los últimos 7 días.")
	public ResponseEntity<List<HistorialCaloriasDto>> resumenCalorias7dias (@AuthenticationPrincipal Usuario usuario) {
		
		List<HistorialCaloriasDto> respuestaDto = diarioService.getConsumoUltimos7Dias(usuario);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
}
