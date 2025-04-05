package easyfit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import easyfit.models.dtos.valornutricional.MacrosRequestDto;
import easyfit.models.dtos.valornutricional.ValorNutriconalResponseDto;
import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.models.entities.ValorNutricional;
import easyfit.repositories.IUsuarioRepository;
import easyfit.repositories.IValorNutricionalRepository;
import easyfit.services.IValorNutricionalService;
import easyfit.utils.ObjetivoCalculator;

@Service
public class ValorNutricionalServiceImpl extends GenericCrudServiceImpl<ValorNutricional, Integer> implements IValorNutricionalService {

	@Autowired
	private IValorNutricionalRepository valornutricionalRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected IValorNutricionalRepository getRepository() {
		return valornutricionalRepository;
	}

	/// METODO PARA CALCULAR KCAL EN EL REGISTRO, GUARDARLOS Y PASARLO COMO RESPUESTA
	@Override
	@Transactional
	public ValorNutriconalResponseDto registroMacrosYKcal(String email) {
	    // Buscamos el usuario en la base de datos
	    Usuario usuario = usuarioRepository.findById(email)
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

	    // Obtenemos el objetivo del usuario
	    Objetivo objetivo = usuario.getObjetivo();

	    // Calculamos las kcal necesarias según los datos del objetivo, ya se guardan en el metodo
	     ObjetivoCalculator.calcularKcal(usuario, objetivo, usuario.getValorNutricional());

	    // Accedemos al objeto ValorNutricional asociado al usuario
	    ValorNutricional valores = usuario.getValorNutricional();

	    // Devolvemos un DTO con kcal + macros (gramos y %)
	    return ValorNutriconalResponseDto.builder()
	        .kcalObjetivo(valores.getKcalObjetivo())
	        .proteinas(valores.getProteinas())
	        .carbohidratos(valores.getCarbohidratos())
	        .grasas(valores.getGrasas())
	        .porcentajeProteinas(valores.getPorcentajeProteinas())
	        .porcentajeCarbohidratos(valores.getPorcentajeCarbohidratos())
	        .porcentajeGrasas(valores.getPorcentajeGrasas())
	        .build();
	}
	
	
	
	// METODO PARA ACTUALIZAR LOS MACROS DE UN USUARIO
	@Override
	@Transactional
	public ValorNutriconalResponseDto actualizarMacrosPorPorcentaje(Usuario usuario, MacrosRequestDto dto) {
	    
	    // Verificamos que el usuario llega correctamente
	    if (usuario == null) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario es null");
	    }

	    // Validamos que los porcentajes sumen exactamente 100 (margen mínimo por redondeos)
	    double suma = dto.getPorcentajeProteinas() + dto.getPorcentajeCarbohidratos() + dto.getPorcentajeGrasas();
	    if (Math.abs(suma - 100.0) > 0.01) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La suma de los porcentajes debe ser 100%");
	    }

	    // Actualizamos los valores en la entidad ValorNutricional (relación 1:1 con Usuario)
	    ValorNutricional valores = usuario.getValorNutricional();
	    valores.setPorcentajeProteinas(dto.getPorcentajeProteinas());
	    valores.setPorcentajeCarbohidratos(dto.getPorcentajeCarbohidratos());
	    valores.setPorcentajeGrasas(dto.getPorcentajeGrasas());

	    // Al guardar los nuevos porcentajes, el trigger en la BBDD recalcula automáticamente los gramos
	    valornutricionalRepository.save(valores);

	    // Devolvemos el DTO actualizado con los datos más recientes (leídos de la entidad)
	    return ValorNutriconalResponseDto.builder()
	            .kcalObjetivo(valores.getKcalObjetivo())
	            .proteinas(valores.getProteinas())
	            .carbohidratos(valores.getCarbohidratos())
	            .grasas(valores.getGrasas())
	            .porcentajeProteinas(valores.getPorcentajeProteinas())
	            .porcentajeCarbohidratos(valores.getPorcentajeCarbohidratos())
	            .porcentajeGrasas(valores.getPorcentajeGrasas())
	            .build();
	}


}