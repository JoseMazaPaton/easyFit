package easyfit.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import easyfit.models.dtos.consumidiario.DiarioResponseDto;
import easyfit.models.dtos.consumidiario.HistorialCaloriasDto;
import easyfit.models.dtos.consumidiario.HistorialPesoDto;
import easyfit.models.entities.ConsumoDiario;
import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Progreso;
import easyfit.models.entities.Usuario;
import easyfit.repositories.IConsumoDiarioRepository;
import easyfit.repositories.IProgresoRepository;
import easyfit.services.IConsumoDiarioService;

@Service
public class ConsumoDiarioImplService extends GenericCrudServiceImpl<ConsumoDiario,Integer> implements IConsumoDiarioService{
	
	@Autowired
	private IConsumoDiarioRepository diarioRepository;

	@Autowired
	private IProgresoRepository progresoRepository;

	
	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected  IConsumoDiarioRepository getRepository() {
		return diarioRepository;
	}


	@Override
	public DiarioResponseDto obtenerResumenDiario (Usuario usuario) {
		
		//Guardamos la fecha de hoy
		LocalDate fecha = LocalDate.now();
		
		//Obtenemos el consumo de hoy y lo guardamos
		ConsumoDiario resumen = diarioRepository.findByFechaAndUsuarioEmail(fecha, usuario.getEmail())
				 .orElseThrow(() -> new UsernameNotFoundException("No se encontro un diario con esa fecha y con ese usuario "));
				
		//Creamos un dto de respuesta
		DiarioResponseDto resumenDto = DiarioResponseDto.builder()
				                       .kcalConsumidas(resumen.getKcalConsumidas())
				                       .KcalObjetivo(usuario.getValorNutricional().getKcalObjetivo())
				                       .build();
		//Obtenemos el calcululo de las kcal restantes
		resumenDto.getKcalRestantes();
		
		return resumenDto;
	}
	
	@Override
	public HistorialPesoDto resumenPesosUsuario(Usuario usuario) {

	    // Buscamos el peso inicial ( mas antiguo ) por fecha ascendente
	    Double pesoInicial = progresoRepository
	        .findFirstByUsuario_EmailOrderByFechaCambioAsc(usuario.getEmail())
	        .map(Progreso::getPeso)
	        .orElse(0.0); // Si no hay registros, se devuelve 0

	    // Sacamos los objetivos del usuario autenticado y los validamos
	    Objetivo objetivo = usuario.getObjetivo();
	    if (objetivo == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los objetivos no existen, no se pueden actualizar");
	    }

	    // Obtenemos el peso actual y objetivo
	    Double pesoActual = objetivo.getPesoActual();
	    Double pesoObjetivo = objetivo.getPesoObjetivo();

	    // Calcacular diferencia de kg
	    // Dependiendo del tipo de objetivo (perder, ganar o mantener) cambia el cálculo
	    Double diferenciaKg = 0.0;
	    Double totalPorPerder = 0.0;

	    switch (objetivo.getObjetivoUsuario()) {
	        case PERDERPESO -> {
	            diferenciaKg = pesoInicial - pesoActual;
	            totalPorPerder = pesoInicial - pesoObjetivo;
	        }
	        case GANARPESO -> {
	        	diferenciaKg = pesoActual - pesoInicial;
	            totalPorPerder = pesoObjetivo - pesoInicial;
	        }
	        case MANTENER -> {
	        	diferenciaKg = Math.abs(pesoActual - pesoInicial);
	            totalPorPerder = 3.0; // Tolerancia fija de 1kg (se puede ajustar)
	        }
	    }

	    // Calcular cuentos kg faltan para llegar al objetivo para sacar el porcentaje
	    // Si el total a perder/ganar es > 0, se calcula el % de progreso
	    int porcentaje = (totalPorPerder > 0)
	        ? (int) Math.round((diferenciaKg * 100) / totalPorPerder)
	        : 0;

	    // Devolvemos una respuesta en formato dto
	    return HistorialPesoDto.builder()
	        .pesoInicial(pesoInicial)
	        .pesoActual(pesoActual)
	        .pesoObjetivo(pesoObjetivo)
	        .diferenciaKg(diferenciaKg)
	        .porcentajeProgreso(porcentaje)
	        .build();
	}
	
	@Override
	public List<HistorialCaloriasDto> getConsumoUltimos7Dias(Usuario usuario) {
	    
	    // Calcular las fechas, hoy y los 6 dias anteriores
	    LocalDate hoy = LocalDate.now();
	    LocalDate hace7dias = hoy.minusDays(6);

	    // Guardamos las kcal objetivo del usuario
	    int kcalObjetivo = usuario.getValorNutricional().getKcalObjetivo();

	    // Obtener el consumo de kcal buscando por las fechas anteriores
	    List<ConsumoDiario> listaCalorias = diarioRepository.findByUsuarioEmailAndFechaBetweenOrderByFechaAsc(usuario.getEmail(), hace7dias, hoy);

	    // Devolver respuesta Dto
	    return listaCalorias.stream()
	        .map(c -> HistorialCaloriasDto.builder()
	            .fecha(c.getFecha()) 
	            .kcalObjetivo(kcalObjetivo)
	            .kcalConsumidas(c.getKcalConsumidas())
	            .build())
	        .collect(Collectors.toList());
	}



}