package easyfit.restcontroller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import easyfit.models.dtos.MacrosRequestDto;
import easyfit.models.dtos.ObjetivoResponseDto;
import easyfit.models.dtos.ValorNutriconalResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.services.ValorNutricionalService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/objetivos")
@CrossOrigin(origins = "*")
public class ObjetivosRestController {

	@Autowired
	private ValorNutricionalService objetivoService;
	
	@Autowired 
	private ModelMapper mapper;
	
	//	- **GET /usuarios/misobjetivos** — Obtener todos los objetivos del usuario
	//	- **PUT /usuarios/misobjetivos/macros** — Cambiar macros de mis objetivos
	//	- **PUT /usuarios/misobjetivos/metapeso** — Cambiar el peso actual.
	//	- **PUT /usuarios/misobjetivos/metapeso** — Cambiar el peso objetivo.
	//	- **PUT /usuarios/misobjetivos/nivelactividad** — Cambiar nivel de actividad física.
	//	- **PUT /usuarios/misobjtivos/metasemanal** — Ajustar ritmo semanal de cambio de peso.
	//	- **PUT /usuarios/misobjetivos/metaobjetivo** — Establecer objetivo general (perder, mantener, ganar).

	//RUTA CON METODO PARA OBTENER TODOS LOS OBJETIVOS DEL USUARIO
	@GetMapping("/misobjetivos")
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
	
	
	
}
