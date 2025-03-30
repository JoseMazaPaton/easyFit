package easyfit.restcontroller;

import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import easyfit.models.dtos.AlimentoDto;
import easyfit.models.dtos.AlimentoEnComidaDto;
import easyfit.models.entities.Alimento;
import easyfit.services.impl.AlimentoImplService;

@RestController
@RequestMapping("/alimentos")
@CrossOrigin(origins = "*")
public class AlimentoRestController {
	
	 @Autowired
	 private AlimentoImplService alimentoService;
	 
	 @GetMapping("")
	 public ResponseEntity<List<AlimentoDto>> buscarAlimentos(
	            @RequestParam(name = "search", required = false) String search) {
	        
	        List<AlimentoDto> alimentosDto = alimentoService.buscarPorNombre(search);
	        return ResponseEntity.ok(alimentosDto);
	    }

	 
	 @GetMapping("/mis")
	 public ResponseEntity<List<AlimentoDto>> obtenerMisAlimentos() {
	     String email = SecurityContextHolder.getContext().getAuthentication().getName();
	     List<AlimentoDto> alimentosDto = alimentoService.buscarPorUsuario(email);
	     return ResponseEntity.ok(alimentosDto);
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
}
