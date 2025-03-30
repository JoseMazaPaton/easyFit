package easyfit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import easyfit.models.dtos.KcalYMacrosDto;
import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Sexo;
import easyfit.repositories.IObjetivoRepository;
import easyfit.repositories.IUsuarioRepository;
import easyfit.services.IObjetivoService;

@Service
public class ObjetivoServiceImpl extends GenericCrudServiceImpl<Objetivo, Integer> implements IObjetivoService {

	@Autowired
	private IObjetivoRepository objetivoRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected IObjetivoRepository getRepository() {
		return objetivoRepository;
	}

	
	///METODO PARA CALCULAR KCAL Y MACROS -- INTENTANDO QUE SEA LO MAS REUTILIZABLE POSIBLE
	@Override
	@Transactional
	public KcalYMacrosDto calcularKcalYMacrosObjetivo(String email) {
	    // Buscamos al usuario y su objetivo con su email
	    Usuario usuario = usuarioRepository.findById(email)
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

	    Objetivo objetivo = usuario.getObjetivo();

	    // Calculamos las kcal totales usando la fórmula de Harris-Benedict
	    double tmb;
	    // Si el usuario es hombre, aplicamos la fórmula para hombres
	    if (usuario.getSexo() == Sexo.HOMBRE) {
	        tmb = 10 * objetivo.getPesoActual() + 6.25 * usuario.getAltura() - 5 * usuario.getEdad() + 5;
	    } else { // Si es mujer, aplicamos la fórmula para mujeres
	        tmb = 10 * objetivo.getPesoActual() + 6.25 * usuario.getAltura() - 5 * usuario.getEdad() - 161;
	    }

	    // Dependiendo de la actividad del usuario, multiplicamos por un factor de actividad física
	    double factorActividad = switch (objetivo.getActividad()) {
	        case SEDENTARIO -> 1.2;
	        case LIGERO -> 1.375;
	        case MODERADO -> 1.55;
	        case ACTIVO -> 1.725;
	        case MUYACTIVO -> 1.9;
	    };

	    // Calculamos el mantenimiento de kcal según el TMB y la actividad física
	    double mantenimiento = tmb * factorActividad;

	    // Ajuste de kcal según el objetivo (ganar o perder peso)
	    double ajuste = (objetivo.getOpcionPeso().getValorKg() * 7700) / 7;
	    double kcal = switch (objetivo.getObjetivoUsuario()) {
	        case PERDERPESO -> mantenimiento - ajuste; // Si el objetivo es perder peso, restamos kcal
	        case GANARPESO -> mantenimiento + ajuste; // Si el objetivo es ganar peso, sumamos kcal
	        case MANTENER -> mantenimiento; // Si el objetivo es mantener peso, no ajustamos
	    };

	    // Calculamos los macros (proteínas, carbohidratos y grasas) con un reparto estándar
	    double proteinas = kcal * 0.20 / 4; // 20% de proteínas
	    double carbohidratos = kcal * 0.50 / 4; // 50% de carbohidratos
	    double grasas = kcal * 0.30 / 9; // 30% de grasas   

	    // Redondeamos los valores a 2 decimales
	    proteinas = Math.round(proteinas * 100.0) / 100.0;
	    carbohidratos = Math.round(carbohidratos * 100.0) / 100.0;
	    grasas = Math.round(grasas * 100.0) / 100.0;

	    // Guardamos los valores calculados en el objetivo actual
	    objetivo.setKcalObjetivo((int) kcal);
	    objetivo.setProteinas(proteinas);
	    objetivo.setCarbohidratos(carbohidratos);
	    objetivo.setGrasas(grasas);

	    // Guardamos los cambios del objetivo en la base de datos
	    objetivoRepository.save(objetivo);

	    // Devolvemos un Dto con las kcal y los macros
	    return new KcalYMacrosDto((int)kcal, proteinas, carbohidratos, grasas);
	}
}