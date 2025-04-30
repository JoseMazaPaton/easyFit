package easyfit.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import easyfit.models.dtos.alimentos.AlimentoEnComidaDto;
import easyfit.models.dtos.comida.ComidaDiariaDto;
import easyfit.models.dtos.comida.ResumenComidaDto;
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
import static easyfit.utils.RoundNumbers.redondear;

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
	    // Obtenemos todas las comidas que ya existen para ese usuario y fecha
	    List<Comida> comidas = comidaRepository.findByFechaAndUsuarioEmail(fecha, emailUsuario);

	    // Si no hay ninguna comida registrada, significa que es un día nuevo → creamos las 3 por defecto
	    if (comidas.isEmpty()) {

	        // Buscamos al usuario asociado
	        Usuario usuario = usuarioRepository.findById(emailUsuario)
	            .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

	        // Definimos los nombres y orden predefinido de las comidas
	        String[] nombres = {"Desayuno", "Comida", "Cena"};

	        // Creamos las 3 comidas básicas para ese día
	        for (int i = 0; i < nombres.length; i++) {
	            Comida nueva = Comida.builder()
	                .usuario(usuario)
	                .fecha(fecha)
	                .nombre(nombres[i])
	                .orden(i + 1)
	                .build();

	            comidaRepository.save(nueva);
	        }

	        // Volvemos a consultar ya incluyendo las recién creadas
	        comidas = comidaRepository.findByFechaAndUsuarioEmail(fecha, emailUsuario);
	    }

	    // Convertimos las comidas a DTOs y las devolvemos
	    return comidas.stream()
	        .map(this::convertirAComidaDiariaDto)
	        .collect(Collectors.toList());
	}


    private ComidaDiariaDto convertirAComidaDiariaDto(Comida comida) {
        List<ComidaAlimento> alimentosEnComida = comidaAlimentoRepository.findByComidaIdComida(comida.getIdComida());
        
        return ComidaDiariaDto.builder()
                .idComida(comida.getIdComida())
                .nombre(comida.getNombre())
                .fecha(comida.getFecha())
                .orden(comida.getOrden())
                .alimentos(convertirAlimentosADto(alimentosEnComida))
                .build();
    }

    private List<AlimentoEnComidaDto> convertirAlimentosADto(List<ComidaAlimento> alimentosEnComida) {
        return alimentosEnComida.stream()
            .map(ca -> {
                Alimento alimento = ca.getAlimento();
                double cantidad = ca.getCantidad();

                return AlimentoEnComidaDto.builder()
                    .idAlimento(alimento.getIdAlimento())
                    .nombre(alimento.getNombre())
                    .marca(alimento.getMarca())
                    .cantidad(cantidad)
                    .unidadMedida(alimento.getUnidadMedida())
                    .kcal((int) Math.round(alimento.getKcal() * cantidad))
                    .proteinas(redondear(alimento.getProteinas() * cantidad))
                    .carbohidratos(redondear(alimento.getCarbohidratos() * cantidad))
                    .grasas(redondear(alimento.getGrasas() * cantidad))
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

	    // Obtener datos nutricionales a restar
	    Alimento alimento = comidaAlimento.getAlimento();
	    double cantidad = comidaAlimento.getCantidad(); // en porciones (por ejemplo 1.2 significa 120g)

	    int kcalARestar = (int) Math.round(alimento.getKcal() * cantidad);
	    double proteinasARestar = redondear(alimento.getProteinas() * cantidad);
	    double carbohidratosARestar = redondear(alimento.getCarbohidratos() * cantidad);
	    double grasasARestar = redondear(alimento.getGrasas() * cantidad);

	    // Buscar el consumo diario del usuario
	    ConsumoDiario consumo = consumoDiarioRepository.findByFechaAndUsuarioEmail(comidaAlimento.getComida().getFecha(), comidaAlimento.getComida().getUsuario().getEmail())
	        .orElse(null);

	    if (consumo != null) {
	        consumo.setKcalConsumidas(Math.max(0, consumo.getKcalConsumidas() - kcalARestar));
	        consumo.setProteinas(Math.max(0, consumo.getProteinas() - proteinasARestar));
	        consumo.setCarbohidratos(Math.max(0, consumo.getCarbohidratos() - carbohidratosARestar));
	        consumo.setGrasas(Math.max(0, consumo.getGrasas() - grasasARestar));
	        consumoDiarioRepository.save(consumo);
	    }

	    // Finalmente, eliminar el alimento de la comida
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
	
	
	private int calcularTotalKcal(List<ComidaAlimento> alimentos) {
	    return alimentos.stream()
	        .mapToInt(ca -> (int) Math.round(ca.getAlimento().getKcal() * ca.getCantidad()))
	        .sum();
	}

	private double calcularTotalProteinas(List<ComidaAlimento> alimentos) {
	    return alimentos.stream()
	        .mapToDouble(ca -> redondear(ca.getAlimento().getProteinas() * ca.getCantidad()))
	        .sum();
	}

	private double calcularTotalCarbohidratos(List<ComidaAlimento> alimentos) {
	    return alimentos.stream()
	        .mapToDouble(ca -> redondear(ca.getAlimento().getCarbohidratos() * ca.getCantidad()))
	        .sum();
	}

	private double calcularTotalGrasas(List<ComidaAlimento> alimentos) {
	    return alimentos.stream()
	        .mapToDouble(ca -> redondear(ca.getAlimento().getGrasas() * ca.getCantidad()))
	        .sum();
	}
	
}
