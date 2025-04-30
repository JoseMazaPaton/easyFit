package easyfit.restcontroller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import easyfit.models.dtos.objetivo.AjusteSemanalDto;
import easyfit.models.dtos.objetivo.NivelActividadDto;
import easyfit.models.dtos.objetivo.ObjetivoResponseDto;
import easyfit.models.dtos.objetivo.ObjetivoUsuarioDto;
import easyfit.models.dtos.objetivo.PesoActualDto;
import easyfit.models.dtos.objetivo.PesoObjetivoDto;
import easyfit.models.dtos.valornutricional.MacrosRequestDto;
import easyfit.models.dtos.valornutricional.ValorNutriconalResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.services.IObjetivoService;
import easyfit.services.IProgresoService;
import easyfit.services.IValorNutricionalService;
import easyfit.utils.ObjetivoCalculator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/objetivos")
@Tag(name = "Objetivos", description = "Operaciones relacionadas con los objetivos de los Usuarios de Easyfit.")
@CrossOrigin(origins = "*")
public class ObjetivosRestController {

	@Autowired
	private IObjetivoService objetivoService;
	
	@Autowired
	private IValorNutricionalService valorNutricionalService;
	
	@Autowired
	private IProgresoService progresoService;
	
	@Autowired 
	private ModelMapper mapper;
	


//	- **GET /objetivos/misobjetivos** — Obtener todos los objetivos actuales del usuario (kcal, macros, peso, objetivo, etc.).
//	- **PUT /objetivos/macros** — Cambiar macros de los objetivos (proteínas, carbohidratos, grasas).
//	- **PUT /objetivos/pesoactual** — Cambiar el peso objetivo.
//	- **PUT /objetivos/pesometa** — Cambiar el peso objetivo.
//	- **PUT /objetivos/nivelactividad** — Cambiar nivel de actividad física.
//	- **PUT /objetivos/metasemanal** — Ajustar ritmo semanal de cambio de peso.
//	- **PUT /objetivos/metaobjetivo** — Establecer objetivo general (perder, mantener, ganar).




	//RUTA CON METODO PARA OBTENER TODOS LOS OBJETIVOS DEL USUARIO
	@GetMapping("/misobjetivos")
	@Operation(summary = "Obtener objetivos de Usuario", description = "Obtiene todos los objetivos actuales del usuario: kcal, macros, peso, objetivo, etc.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Objetivos obtenidos correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjetivoResponseDto.class))),
        @ApiResponse(responseCode = "500", description = "Error al obtener objetivos", content = @Content)
    })
	public ResponseEntity<ObjetivoResponseDto> objetivosUsuario(@AuthenticationPrincipal Usuario usuario) {
	
		//Mapeamos los objetivos del usuario en una respuesta dto
		ObjetivoResponseDto respuestaDto = mapper.map(usuario.getObjetivo(),ObjetivoResponseDto.class);
		
		//Le añadimos los valores nutricionales y la coherencia de los datos a la respuesta 
	    respuestaDto.setValores(mapper.map(usuario.getValorNutricional(), ValorNutriconalResponseDto.class));
	    respuestaDto.setCoherente(ObjetivoCalculator.esObjetivoCoherente(usuario.getObjetivo()));
		
		
		return ResponseEntity.ok(respuestaDto);	
	}

	//RUTA CON METODO PARA MODIFICAR LOS MACROS DEL USUARIO POR PORCENTAJE
	@PutMapping("/macros")
	@Operation(summary = "Modificar macronutrientes", description = "Modifica los macronutrientes del usuario (proteínas, carbohidratos, grasas) en porcentaje.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Macronutrientes modificados correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValorNutriconalResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
	public ResponseEntity<ValorNutriconalResponseDto> modificarMacros(
			@RequestBody @Valid MacrosRequestDto macrosDto,  
			@AuthenticationPrincipal Usuario usuario) {

		//Actualizamos los macronutrientes y guardamos la respuesta
		ValorNutriconalResponseDto  respuestaDto =  valorNutricionalService.actualizarMacrosPorPorcentaje(usuario, macrosDto);
		
		return ResponseEntity.ok(respuestaDto);	
	}
	

	
	//REGISTRO DE NUEVO PESO
	@PutMapping("/pesoactual")
	@Operation(summary = "Actualizar peso actual", description = "Registra un nuevo peso actual del usuario y lo guarda en el historial de progreso.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Peso actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjetivoResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
	public ResponseEntity<ObjetivoResponseDto> actualizarPesoActual(
			@RequestBody @Valid PesoActualDto pesoDto, 
			@AuthenticationPrincipal Usuario usuario) {
		
		//Actualizamos el peso con metodo del service
		ObjetivoResponseDto respuestaDto = objetivoService.actualizarPesoActual(pesoDto, usuario);
		double pesoProgreso = pesoDto.getPesoActual();
		
		progresoService.registrarNuevoPeso(pesoProgreso, usuario);
			
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	//ACTUALIZAR PESO OBJETIVO - NO MODIFICA LAS KCAL!
	@PutMapping("/pesometa")
	@Operation(summary = "Modificar peso objetivo", description = "Modifica el peso objetivo del usuario sin alterar las calorías.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Peso objetivo actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjetivoResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
	public ResponseEntity<ObjetivoResponseDto> actualizarPesoObjetivo(
			@RequestBody @Valid PesoObjetivoDto pesoDto, 
			@AuthenticationPrincipal Usuario usuario) {

		//Actualizamos el peso con el metodo del service
		ObjetivoResponseDto respuestaDto = objetivoService.actualizarPesoObjetivo(pesoDto, usuario);
			
		return ResponseEntity.ok(respuestaDto);
	}
	
	//ACTUALIZAR NIVEL DE ACTIVIDAD
	@PutMapping("/nivelactividad")
	@Operation(summary = "Modificar nivel de actividad", description = "Modifica el nivel de actividad física del usuario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nivel de actividad actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjetivoResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
	public ResponseEntity<ObjetivoResponseDto>  actualizarNivelActividad(
			@RequestBody @Valid NivelActividadDto actividadDto, 
			@AuthenticationPrincipal Usuario usuario ) {
	
		//Actualizamos la actividad con el metodo del service
		ObjetivoResponseDto respuestaDto = objetivoService.actualizarActividad(actividadDto, usuario);
		
		return ResponseEntity.ok(respuestaDto);
	}
	

	//ACTUALIZAR OPCION PESO
	@PutMapping("/metasemanal")
	@Operation(summary = "Modificar meta semanal", description = "Ajusta el ritmo semanal de cambio de peso del usuario (ganar/perder por semana).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Meta semanal actualizada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjetivoResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
	public ResponseEntity<ObjetivoResponseDto>  actualizarOpcionPeso(
			@RequestBody @Valid AjusteSemanalDto ajusteDto, 
			@AuthenticationPrincipal Usuario usuario ) {
		
		//Actualizamos la opcion de peso semanal con el metodo del service
		ObjetivoResponseDto respuestaDto = objetivoService.actualizarOpcionPeso(ajusteDto, usuario);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	//ACTUALIZAR NIVEL DE ACTIVIDAD
	@PutMapping("/metaobjetivo")
	@Operation(summary = "Modificar objetivo general", description = "Modifica el objetivo general del usuario: perder peso, mantener o ganar masa.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Objetivo actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjetivoResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
	public ResponseEntity<ObjetivoResponseDto> actualizarObjetivoUsuario (
			@RequestBody @Valid ObjetivoUsuarioDto objetivoDto,
			@AuthenticationPrincipal Usuario usuario ) {
		
		//Actualizamos la opcion de peso semanal con el metodo del service
		ObjetivoResponseDto respuestaDto = objetivoService.actualizarObjetivoUsuario(objetivoDto, usuario);
		
		return ResponseEntity.ok(respuestaDto);
	}
	

}
