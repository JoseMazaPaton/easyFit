package easyfit.services.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import easyfit.models.entities.Progreso;
import easyfit.models.entities.Usuario;
import easyfit.repositories.IProgresoRepository;
import easyfit.repositories.IUsuarioRepository;
import easyfit.services.IProgresoService;

@Service
public class ProgresoImplService extends GenericCrudServiceImpl<Progreso,Integer> implements IProgresoService{
	
	@Autowired
	private IProgresoRepository progresoRepository;
	
	@Autowired
    private IUsuarioRepository usuarioRepository;
	
	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected IProgresoRepository getRepository() {
		return progresoRepository;
	}
	
	
	@Override
    public Map<String, Double> obtenerProgresoPeso(String emailUsuario) {
        // Obtener usuario
        Usuario usuario = usuarioRepository.findById(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Obtener último registro de peso
        Optional<Progreso> progresos = progresoRepository.findFirstByUsuario_EmailOrderByFechaCambioAsc(emailUsuario);
        Double pesoActual = progresos.isEmpty() ? null : progresos.get().getPeso();
        
     //   // Obtener peso objetivo (asumiendo que está en el usuario)
     //   Double pesoObjetivo = usuario.getPesoObjetivo(); // Necesitarías agregar este campo en la entidad Usuario
        
        Map<String, Double> resultado = new HashMap<>();
        if(pesoActual != null) resultado.put("pesoActual", pesoActual);
      //  if(pesoObjetivo != null) resultado.put("pesoObjetivo", pesoObjetivo);
        
        return resultado;
    }


	@Override
	public Progreso registrarNuevoPeso(double peso, Usuario usuario) {
		// Validación básica
        if(peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0");
        }
        
        Progreso nuevoProgreso = Progreso.builder()
                .peso(peso)
                .usuario(usuario)
                .fechaCambio(LocalDateTime.now())
                .build();
        
        return progresoRepository.save(nuevoProgreso);
    }


}
