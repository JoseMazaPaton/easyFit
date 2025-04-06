package easyfit.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import easyfit.models.dtos.consumidiario.DiarioResponseDto;
import easyfit.models.dtos.consumidiario.HistorialCaloriasDto;
import easyfit.models.dtos.consumidiario.HistorialPesoDto;
import easyfit.models.entities.Usuario;
import easyfit.services.IConsumoDiarioService;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardRestController {

	@Autowired
	private IConsumoDiarioService diarioService;
	
	//METODO CON RUTA RESUMEN DE KCAL DEL DIA DEL USUARIO
	@GetMapping("/resumendiario")
	public ResponseEntity<DiarioResponseDto> resumenDiarioUsuario (@AuthenticationPrincipal Usuario usuaurio) {
		
		DiarioResponseDto respuestaDto =  diarioService.obtenerResumenDiario(usuaurio);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	//METODO QUE OBJETIENE RESUMEN DE PESO ACUTUAL, PESO OBJETIVO , PESO INICIAL - PARA GRAFICO CIRCULAR EN EL FRONT	
	@GetMapping("/resumenpesos")
	public ResponseEntity<HistorialPesoDto> resumenPesosUsuario(@AuthenticationPrincipal Usuario usuario) {
		
		HistorialPesoDto respuestaDto = diarioService.resumenPesosUsuario(usuario);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	@GetMapping("/resumencalorias")
	public ResponseEntity<List<HistorialCaloriasDto>> resumenCalorias7dias (@AuthenticationPrincipal Usuario usuario) {
		
		List<HistorialCaloriasDto> respuestaDto = diarioService.getConsumoUltimos7Dias(usuario);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	
}
