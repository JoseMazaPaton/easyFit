package easyfit.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import easyfit.models.dtos.alimentos.AlimentoDto;
import easyfit.models.entities.Alimento;
import easyfit.models.entities.Categoria;
import easyfit.models.entities.Usuario;
import easyfit.services.impl.AlimentoImplService;
import easyfit.services.impl.CategoriaImplService;
import easyfit.services.impl.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@RequestMapping("/alimentos")
@Tag(name = "Alimentos", description = "Operaciones relacionadas con los alimentos de Easyfit.")
@CrossOrigin(origins = "*")
public class AlimentoRestController {
	
	 @Autowired
	 private AlimentoImplService alimentoService;
	 
	 @Autowired
	 private CategoriaImplService categoriaService;
	 
	 @Autowired
	 private UsuarioServiceImpl usuarioService;
	 
	 
	 /*
	  *  Se puede buscar alimentos o añadir el nombre del alimento por nombre
	  */
	 @Operation(summary = "Buscar alimentos", description = "Busca alimentos por nombre opcionalmente filtrado.")
	    @ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Lista de alimentos obtenida",
	                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlimentoDto.class))),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	    })
	 @GetMapping("")
	 public ResponseEntity<List<AlimentoDto>> buscarAlimentos(
	            @RequestParam(name = "search", required = false) String search) {
	        
	        List<AlimentoDto> alimentosDto = alimentoService.buscarPorNombre(search);
	        return ResponseEntity.ok(alimentosDto);
	    }

	 
	 
	 /*
	  *  Saca los alimentos creados por el propio usuario
	  */
	 @Operation(summary = "Obtener mis alimentos", description = "Recupera los alimentos creados por el usuario autenticado.")
	    @ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Alimentos del usuario obtenidos",
	                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlimentoDto.class))),
	        @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	    })
	 @GetMapping("/mis")
	 public ResponseEntity<List<AlimentoDto>> obtenerMisAlimentos() {
	     String email = SecurityContextHolder.getContext().getAuthentication().getName();
	     List<AlimentoDto> alimentosDto = alimentoService.buscarPorUsuario(email);
	     return ResponseEntity.ok(alimentosDto);
	 }
	 
	 
	 /*
	  * Crear alimento personalizado (el usuario es el `creado_por`).
	  */
	 @Operation(summary = "Crear alimento personalizado", description = "El usuario autenticado crea un nuevo alimento.")
	    @ApiResponses({
	        @ApiResponse(responseCode = "201", description = "Alimento creado exitosamente"),
	        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	    })
	 @PostMapping("")
	 public ResponseEntity<?> altaAlimentoUsuario(@Parameter(description = "Alimento a crear", required = true)
			 @RequestBody AlimentoDto alimentoDto) {
		 Categoria categoria = categoriaService.findById(alimentoDto.getIdCategoria());

		 if (categoria == null) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND)
					 .body("La categoria no existe");
		 }

		 Alimento alimento = new Alimento();
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
		 
		 Usuario usuario = usuarioService.findById(email);
		 
		 
		 alimento.setNombre(alimentoDto.getNombre());
		 alimento.setMarca(alimentoDto.getMarca());
		 alimento.setKcal(alimentoDto.getKcal());
		 alimento.setProteinas(alimentoDto.getProteinas());
		 alimento.setCarbohidratos(alimentoDto.getCarbohidratos());
		 alimento.setGrasas(alimentoDto.getGrasas());
		 alimento.setUnidadMedida(alimentoDto.getUnidadMedida());
		 alimento.setCreadoPor(usuario);
		 alimento.setCategoria(categoria);
		 
		 
		 alimentoService.crearAlimento(alimento);
		 
		 return ResponseEntity.status(HttpStatus.CREATED)
				 .body("Alimento creado con exito.");
	 }
	 
	 
	 /*
	  * Editar alimento (solo si el usuario es el creador).
	  */
	 @Operation(summary = "Modificar alimento", description = "Modifica un alimento si el usuario autenticado es su creador.")
	    @ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Alimento modificado correctamente"),
	        @ApiResponse(responseCode = "404", description = "Alimento o categoría no encontrados"),
	        @ApiResponse(responseCode = "409", description = "Usuario no autorizado para modificar"),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	    })
	 @PutMapping("/{idAlimento}")
	 public ResponseEntity<?> modificarAlimentoUsuario (@Parameter(description = "ID del alimento a modificar.", required = true)
			 																					@PathVariable int idAlimento,
			 																					@RequestBody AlimentoDto alimentoDto) {
		 
		 Alimento alimento = alimentoService.findById(idAlimento);
		 System.out.println(alimento.getCreadoPor().getEmail());
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
		 System.out.println(email);
		 
		 if (!alimento.getCreadoPor().getEmail().equals(email)) {
			 return ResponseEntity.status(HttpStatus.CONFLICT)
					 .body("No eres el creador de ese alimento, no puedes modificarlo.");
		 }
		 
		 Categoria categoria = categoriaService.findById(alimentoDto.getIdCategoria());
		 
		 alimento.setNombre(alimentoDto.getNombre());
		 alimento.setMarca(alimentoDto.getMarca());
		 alimento.setKcal(alimentoDto.getKcal());
		 alimento.setProteinas(alimentoDto.getProteinas());
		 alimento.setCarbohidratos(alimentoDto.getCarbohidratos());
		 alimento.setGrasas(alimentoDto.getGrasas());
		 alimento.setUnidadMedida(alimentoDto.getUnidadMedida());
		 alimento.setCategoria(categoria);
		 
		 alimentoService.crearAlimento(alimento);
		 
		 return ResponseEntity.status(HttpStatus.OK)
				 .body("Alimento modificado con éxito.");
	 }
	 
	 
	 
	 /*
	  * Eliminar alimento (solo si el usuario es el creador).
	  */
	 @Operation(summary = "Eliminar alimento", description = "Elimina un alimento si el usuario autenticado es su creador.")
	    @ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Alimento eliminado correctamente"),
	        @ApiResponse(responseCode = "404", description = "Alimento no encontrado"),
	        @ApiResponse(responseCode = "409", description = "Usuario no autorizado para eliminar"),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	    })
	 @DeleteMapping("/{idAlimento}")
	 public ResponseEntity<?> eliminarAlimentoUsuario (@Parameter(description = "ID del alimento a crear", required = true)
			 																				@PathVariable int idAlimento) {
		 
		 if (alimentoService.findById(idAlimento) == null) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND)
					 .body("El alimento que quiere eliminar no existe.");
		 }
		 
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
		 
		 if (!alimentoService.findById(idAlimento).getCreadoPor().getEmail().equals(email)) {
			 return ResponseEntity.status(HttpStatus.CONFLICT)
					 .body("No eres el creador de ese alimento, no puedes eliminarlo.");
		 }
		 
		 alimentoService.deleteOne(idAlimento);
		 
		 return ResponseEntity.status(HttpStatus.OK)
				 .body("El alimento ha sido eliminado con éxito.");
	 }
	 
	 
	 /*
	  * Calcular kcal y macros según cantidad.
	  */
	 @Operation(summary = "Calcular macronutrientes", description = "Calcula kcal y macros según gramos de un alimento.")
	    @ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Cálculo realizado correctamente",
	                     content = @Content(mediaType = "application/json")),
	        @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
	        @ApiResponse(responseCode = "404", description = "Alimento no encontrado"),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	    })
	 @GetMapping("/{idAlimento}/calculo")
	 public ResponseEntity<?> calculoKcalMacro (@Parameter(description = "ID del alimento elegido para calcular los macronutrientes.", required = true)
												 @PathVariable int idAlimento,
												 @Parameter(description = "Cantidad en gramos", required = true)
												 @RequestParam(name = "gramos", required = true) Double gramos) {
											 
		 
		 if (gramos < 0) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					 .body("La cantidad en gramos no puede ser menor que 0");
		 }
		 		 
		 Alimento alimento = alimentoService.findById(idAlimento);
		 
		 if (alimento == null) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND)
					 .body("El alimento que quiere calcular no existe.");
		 }
		 
		 double kcal = (gramos / 100) * alimento.getKcal();
	     double proteinas = (gramos / 100) * alimento.getProteinas();
	     double carbohidratos = (gramos / 100) * alimento.getCarbohidratos();
	     double grasas = (gramos / 100) * alimento.getGrasas();
		 
	     Map<String, Object> response = new HashMap<>();
	        response.put("alimento", alimento.getNombre());
	        response.put("kcal", kcal);
	        response.put("proteinas", proteinas);
	        response.put("carbohidratos", carbohidratos);
	        response.put("grasas", grasas);

	        return ResponseEntity.ok(response);
		 
	     
	 }
	
}
