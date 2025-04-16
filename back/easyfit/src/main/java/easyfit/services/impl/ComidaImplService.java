package easyfit.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import easyfit.models.dtos.alimentos.AlimentoEnComidaDto;
import easyfit.models.dtos.auth.ResumenComidaDto;
import easyfit.models.dtos.comida.ComidaDiariaDto;
import easyfit.models.entities.Alimento;
import easyfit.models.entities.Comida;
import easyfit.models.entities.ComidaAlimento;
import easyfit.models.entities.ConsumoDiario;
import easyfit.models.entities.Usuario;
import easyfit.repositories.IAlimentoRepository;
import easyfit.repositories.IComidaAlimentoRepository;
import easyfit.repositories.IComidaRepository;
import easyfit.repositories.IConsumoDiarioRepository;
import easyfit.repositories.IUsuarioRepository;
import easyfit.services.IComidaService;
import jakarta.transaction.Transactional;

@Service
public class ComidaImplService extends GenericCrudServiceImpl<Comida, Integer> implements IComidaService{

	@Autowired
	private IComidaRepository comidaRepository;
	
	@Autowired
    private IComidaAlimentoRepository comidaAlimentoRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IAlimentoRepository alimentoRepository;
	
	@Autowired
	private IConsumoDiarioRepository consumoDiarioRepository;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected  IComidaRepository getRepository() {
		return comidaRepository;
	}

	

    @Override
    public List<ComidaDiariaDto> obtenerComidasDelDia(LocalDate fecha, String emailUsuario) {
    	
        List<Comida> comidas = comidaRepository.findByFechaAndUsuarioEmail(fecha, emailUsuario);
        
        return comidas.stream()
                .map(this::convertirAComidaDiariaDto)
                .collect(Collectors.toList());
    }
    
    

    private ComidaDiariaDto convertirAComidaDiariaDto(Comida comida) {
        List<ComidaAlimento> alimentosEnComida = comidaAlimentoRepository.findByComidaIdComida(comida.getIdComida());
        
        return ComidaDiariaDto.builder()
                .idComida(comida.getIdComida())
                .nombre(comida.getNombre())
                .orden(comida.getOrden())
                .alimentos(convertirAlimentosADto(alimentosEnComida))
                .build();
    }

    private List<AlimentoEnComidaDto> convertirAlimentosADto(List<ComidaAlimento> alimentosEnComida) {
        
    	 return alimentosEnComida.stream()
    		        .map(ca -> {
    		            Alimento alimento = ca.getAlimento();
    		            
    		            return AlimentoEnComidaDto.builder()
    		                .idAlimento(alimento.getIdAlimento())
    		                .nombre(alimento.getNombre())
    		                .marca(alimento.getMarca())
    		                .cantidad(ca.getCantidad())
    		                .unidadMedida(alimento.getUnidadMedida())
    		                .kcal((int) (alimento.getKcal() * (ca.getCantidad() / 100.0)))
    		                .proteinas(alimento.getProteinas() * (ca.getCantidad() / 100.0))
    		                .carbohidratos(alimento.getCarbohidratos() * (ca.getCantidad() / 100.0))
    		                .grasas(alimento.getGrasas() * (ca.getCantidad() / 100.0))
    		                .build();
    		        })
    		        .collect(Collectors.toList());
    		}

    @Override
    public Comida crearComida(Comida comida) {
        // Validaciones básicas
        if(comida.getNombre() == null || comida.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la comida es obligatorio");
        }
        
        if(comida.getOrden() <= 0) {
            throw new IllegalArgumentException("El orden debe ser un número positivo");
        }
        
        // Establecer fecha actual si no viene informada
        if(comida.getFecha() == null) {
            comida.setFecha(LocalDate.now());
        }
        
        // Verificar y cargar usuario completo
        Usuario usuario = usuarioRepository.findById(comida.getUsuario().getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        comida.setUsuario(usuario);
        
        return comidaRepository.save(comida);
    }
    

	@Override
	public void agregarAlimentoAComida(int idComida, int idAlimento, double cantidad) {
	    // Validar cantidad
	    if(cantidad <= 0) {
	        throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
	    }
	    
	    // Obtener comida
	    Comida comida = comidaRepository.findById(idComida)
	        .orElseThrow(() -> new NoSuchElementException("Comida no encontrada"));
	    
	    // Obtener alimento
	    Alimento alimento = alimentoRepository.findById(idAlimento)
	        .orElseThrow(() -> new NoSuchElementException("Alimento no encontrado"));
	    
	    // Crear relación
	    ComidaAlimento comidaAlimento = ComidaAlimento.builder()
	        .comida(comida)
	        .alimento(alimento)
	        .cantidad(cantidad)
	        .build();
	    
	    comidaAlimentoRepository.save(comidaAlimento);
	}
	
	@Override
	public void eliminarAlimentoDeComida(int idComida, int idAlimento) {
	    // Verificar existencia de la relación		
	    ComidaAlimento comidaAlimento = comidaAlimentoRepository.findByComidaIdComidaAndAlimentoIdAlimento(idComida, idAlimento)
	        .orElseThrow(() -> new NoSuchElementException("El alimento no está asociado a esta comida"));
	    
	    comidaAlimentoRepository.delete(comidaAlimento);
	}
	
	@Override
	public void actualizarCantidadAlimento(int idComida, int idAlimento, double nuevaCantidad) {
	    if(nuevaCantidad <= 0) {
	        throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
	    }
	    
	    ComidaAlimento comidaAlimento = comidaAlimentoRepository
	        .findByComidaIdComidaAndAlimentoIdAlimento(idComida, idAlimento)
	        .orElseThrow(() -> new NoSuchElementException("Relación comida-alimento no encontrada"));
	    
	    comidaAlimento.setCantidad(nuevaCantidad);
	    comidaAlimentoRepository.save(comidaAlimento);
	}
	
	@Transactional
	@Override
	public void eliminarComida(int idComida, Usuario usuario) {
	    // Obtener la comida con validación de existencia y pertenencia
	    Comida comida = comidaRepository.findById(idComida)
	        .orElseThrow(() -> new NoSuchElementException("Comida no encontrada"));
	    
	    if(!comida.getUsuario().getEmail().equals(usuario.getEmail())) {
	        throw new SecurityException("No tienes permisos para eliminar esta comida");
	    }
	    
	    // Obtener todos los alimentos asociados
	    List<ComidaAlimento> alimentos = comidaAlimentoRepository.findByComidaIdComida(idComida);
	    
	    // Calcular totales nutricionales
	    double totalKcal = 0;
	    double totalProteinas = 0;
	    double totalCarbohidratos = 0;
	    double totalGrasas = 0;
	    
	    for(ComidaAlimento ca : alimentos) {
	        Alimento alimento = ca.getAlimento();
	        double factor = ca.getCantidad() / 100.0;
	        
	        totalKcal += alimento.getKcal() * factor;
	        totalProteinas += alimento.getProteinas() * factor;
	        totalCarbohidratos += alimento.getCarbohidratos() * factor;
	        totalGrasas += alimento.getGrasas() * factor;
	    }
	    
	    // Actualizar consumo diario
	    ConsumoDiario consumo = consumoDiarioRepository.findByFechaAndUsuarioEmail(comida.getFecha(), usuario.getEmail())
	        .orElseThrow(() -> new IllegalStateException("Registro de consumo diario no encontrado"));
	    
	    consumo.setKcalConsumidas((int) Math.max(0, consumo.getKcalConsumidas() - totalKcal));
	    consumo.setProteinas(Math.max(0, consumo.getProteinas() - totalProteinas));
	    consumo.setCarbohidratos(Math.max(0, consumo.getCarbohidratos() - totalCarbohidratos));
	    consumo.setGrasas(Math.max(0, consumo.getGrasas() - totalGrasas));
	    
	    consumoDiarioRepository.save(consumo);
	    
	    // Eliminar alimentos asociados y la comida
	    comidaAlimentoRepository.deleteAll(alimentos);
	    comidaRepository.delete(comida);
	}
	
	// ComidaServiceImpl.java
	@Override
	public ResumenComidaDto obtenerResumenComida(int idComida, Usuario usuario) {
	    Comida comida = comidaRepository.findById(idComida)
	        .orElseThrow(() -> new NoSuchElementException("Comida no encontrada"));
	    
	    // Validar pertenencia de la comida al usuario
	    if(!comida.getUsuario().getEmail().equals(usuario.getEmail())) {
	        throw new SecurityException("No tienes acceso a esta comida");
	    }
	    
	    List<ComidaAlimento> alimentos = comidaAlimentoRepository.findByComidaIdComida(idComida);
	    
	    return ResumenComidaDto.builder()
	        .nombreComida(comida.getNombre())
	        .fecha(comida.getFecha())
	        .alimentos(convertirAlimentosADto(alimentos))
	        .totalKcal(calcularTotalKcal(alimentos))
	        .totalProteinas(calcularTotalProteinas(alimentos))
	        .totalCarbohidratos(calcularTotalCarbohidratos(alimentos))
	        .totalGrasas(calcularTotalGrasas(alimentos))
	        .build();
	}


	// Métodos de cálculo
	private int calcularTotalKcal(List<ComidaAlimento> alimentos) {
	    return alimentos.stream()
	        .mapToInt(ca -> (int) (ca.getAlimento().getKcal() * (ca.getCantidad() / 100.0)))
	        .sum();
	}

	private double calcularTotalProteinas(List<ComidaAlimento> alimentos) {
	    return alimentos.stream()
	        .mapToDouble(ca -> ca.getAlimento().getProteinas() * (ca.getCantidad() / 100.0))
	        .sum();
	}

	private double calcularTotalCarbohidratos(List<ComidaAlimento> alimentos) {
	    return alimentos.stream()
	        .mapToDouble(ca -> ca.getAlimento().getCarbohidratos() * (ca.getCantidad() / 100.0))
	        .sum();
	}

	private double calcularTotalGrasas(List<ComidaAlimento> alimentos) {
	    return alimentos.stream()
	        .mapToDouble(ca -> ca.getAlimento().getGrasas() * (ca.getCantidad() / 100.0))
	        .sum();
	}
	
}
