package easyfit.restcontroller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import easyfit.models.entities.Categoria;
import easyfit.services.ICategoriaService;



@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriaRestController {
	
	
	@Autowired
	private ICategoriaService categoriaService;

	
	@GetMapping("/todas")
	public ResponseEntity<?> listarTodasCategorias() {
        try {
            List<Categoria> categorias = categoriaService.obtenerTodasCategorias();
            
            if(categorias.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(categorias);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener las categorías: " + e.getMessage());
        }
    }
}
