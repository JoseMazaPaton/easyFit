package easyfit.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import easyfit.models.dtos.consumodiario.DiarioResponseDto;
import easyfit.models.dtos.consumodiario.HistorialCaloriasDto;
import easyfit.models.dtos.consumodiario.HistorialPesoDto;
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
	public DiarioResponseDto obtenerResumenDiario(Usuario usuario) {

	    LocalDate fecha = LocalDate.now();

	    // ✅ Si no hay diario, asumimos consumo 0
	    Optional<ConsumoDiario> resumenOpt = diarioRepository.findByFechaAndUsuarioEmail(fecha, usuario.getEmail());

	    int kcalConsumidas = resumenOpt.map(ConsumoDiario::getKcalConsumidas).orElse(0);

	    DiarioResponseDto resumenDto = DiarioResponseDto.builder()
	            .kcalConsumidas(kcalConsumidas)
	            .KcalObjetivo(usuario.getValorNutricional().getKcalObjetivo())
	            .build();

	    resumenDto.getKcalRestantes(); // si este método realiza lógica interna, mantenlo

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

	    return HistorialPesoDto.builder()
	    	    .pesoInicial(pesoInicial)
	    	    .pesoActual(pesoActual)
	    	    .pesoObjetivo(pesoObjetivo)
	    	    .diferenciaKg(diferenciaKg)
	    	    .porcentajeProgreso(porcentaje)
	    	    .objetivoUsuario(objetivo.getObjetivoUsuario()) // 👈 añadir aquí
	    	    .build();
	}
	
	@Override
	public List<HistorialCaloriasDto> getConsumoUltimos7Dias(Usuario usuario) {
	    
	    LocalDate hoy = LocalDate.now();
	    LocalDate hace7dias = hoy.minusDays(6);

	    int kcalObjetivo = usuario.getValorNutricional().getKcalObjetivo();

	    // Obtener consumos registrados en los últimos 7 días
	    List<ConsumoDiario> listaCalorias = diarioRepository
	        .findByUsuarioEmailAndFechaBetweenOrderByFechaAsc(usuario.getEmail(), hace7dias, hoy);

	    // Mapear los consumos por fecha para buscarlos rápido
	    Map<LocalDate, ConsumoDiario> mapaPorFecha = listaCalorias.stream()
	        .collect(Collectors.toMap(ConsumoDiario::getFecha, c -> c));

	    // Lista final con los 7 días completos
	    List<HistorialCaloriasDto> resultado = new ArrayList<>();

	    for (int i = 0; i <= 6; i++) {
	        LocalDate fecha = hace7dias.plusDays(i);

	        ConsumoDiario consumo = mapaPorFecha.get(fecha);

	        double kcalConsumidas = consumo != null ? consumo.getKcalConsumidas() : 0;

	        resultado.add(HistorialCaloriasDto.builder()
	            .fecha(fecha)
	            .kcalObjetivo(kcalObjetivo)
	            .kcalConsumidas(kcalConsumidas)
	            .build());
	    }

	    return resultado;
	}


}