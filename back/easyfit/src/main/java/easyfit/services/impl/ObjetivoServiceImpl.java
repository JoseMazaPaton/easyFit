package easyfit.services.impl;

import org.hibernate.sql.Update;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import easyfit.models.dtos.objetivo.ObjetivoResponseDto;
import easyfit.models.dtos.objetivo.PesoActualDto;
import easyfit.models.dtos.objetivo.PesoObjetivoDto;
import easyfit.models.dtos.valornutricional.MacrosRequestDto;
import easyfit.models.dtos.valornutricional.ValorNutriconalResponseDto;
import easyfit.models.entities.Categoria;
import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Actividad;
import easyfit.models.enums.ObjetivoUsuario;
import easyfit.models.enums.OpcionPeso;
import easyfit.repositories.IComidaAlimentoRepository;
import easyfit.repositories.IObjetivoRepository;
import easyfit.services.IObjetivoService;
import easyfit.utils.ObjetivoCalculator;
import jakarta.validation.Valid;

import java.util.NoSuchElementException;

@Service
public class ObjetivoServiceImpl extends GenericCrudServiceImpl<Objetivo, Integer> implements IObjetivoService {

    @Autowired
    private IObjetivoRepository objetivoRepository;
    
    @Autowired
    private ModelMapper mapper;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected IObjetivoRepository getRepository() {
		return objetivoRepository;
	}
    
	//METODO PARA ACTUALIZAR EL PESO ACTUAL + RECALCULAR KCAL
	@Override
	public ObjetivoResponseDto actualizarPesoActual(PesoActualDto pesoDto, Usuario usuario) {

	    //Primero comprobamos que los valores que llegan por el body no sean nulos
	    if (pesoDto == null || usuario == null) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El peso o el usuario no pueden ser null");
	    }
	    
	    //Sacamos los objetivos del usuario autenticado y los guardamos 
	    Objetivo objetivo = usuario.getObjetivo();

	    //Comprobamos que no sean null
	    if (objetivo == null) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los objetivos no puede ser null");
	    }

	    //Le añadimos el nuevo peso registrado 
	    objetivo.setPesoActual(pesoDto.getPesoActual());

	    //Con el nuevo peso tenemos que recalcular las Kcal
	    ObjetivoCalculator.calcularKcal(usuario, objetivo, usuario.getValorNutricional());

	    //Actualizamos objetivos con el nuevo peso
	    if (!objetivoRepository.existsById(objetivo.getIdObjetivo())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los objetivos no existen, no se pueden actualizar");
	    } else {
	        updateOne(objetivo); //Usamos el metodo de IGenericCrud para actualizar que ya añade excepciones extra
	    }

	    //Mapeamos la entidad y le añadimos los valores nutricionales de usuario mapeados ( para mostar que han cambiado al recalcular las kcal)
	    ObjetivoResponseDto respuestaDto = mapper.map(objetivo, ObjetivoResponseDto.class);
	    respuestaDto.setValores(mapper.map(usuario.getValorNutricional(), ValorNutriconalResponseDto.class));
	    
	    //Devolvemos la respuesta mapeada a dto
	    return respuestaDto ;
	}

	
	@Override
	public ObjetivoResponseDto actualizarPesoObjetivo(PesoObjetivoDto pesoDto, Usuario usuario) {

	    // Validaciones iniciales
	    if (pesoDto == null || usuario == null) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El peso o el usuario no pueden ser null");
	    }

	    double pesoActual = usuario.getObjetivo().getPesoActual();
	    double pesoObjetivo = pesoDto.getPesoObjetivo(); // CORREGIDO: estabas usando mal el nombre
	    ObjetivoUsuario tipo = usuario.getObjetivo().getObjetivoUsuario();

	    // Validar si el peso objetivo es coherente con el tipo de objetivo del usuario
	    boolean valido = switch (tipo) {
	        case PERDERPESO -> pesoObjetivo < pesoActual;
	        case GANARPESO  -> pesoObjetivo > pesoActual;
	        case MANTENER   -> true;
	    };

	    if (!valido) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
	            "El peso objetivo no es coherente con tu objetivo (" + tipo + ")");
	    }

	    // Verificar existencia del objetivo antes de actualizar
	    if (!objetivoRepository.existsById(usuario.getObjetivo().getIdObjetivo())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los objetivos no existen, no se pueden actualizar");
	    }

	    // Metemos el peso objetivo en los objetivos del usuario y actualizamos
	    usuario.getObjetivo().setPesoObjetivo(pesoObjetivo);
	    updateOne(usuario.getObjetivo());

	    // Devolvemos la respuesta como dto
	    return mapper.map(usuario.getObjetivo(), ObjetivoResponseDto.class) ;
	}

    
    @Override
    public Objetivo actualizarActividad(Actividad nuevaActividad, Usuario usuario) {
        if(nuevaActividad == null) {
            throw new IllegalArgumentException("La actividad no puede ser nula");
        }
        
        Objetivo objetivo = objetivoRepository.findByUsuarioEmail(usuario.getEmail())
            .orElseThrow(() -> new NoSuchElementException("No se encontraron objetivos para el usuario"));
        
        objetivo.setActividad(nuevaActividad);
        return objetivoRepository.save(objetivo);
    }
    
    @Override
    public Objetivo actualizarOpcionPeso(OpcionPeso nuevaOpcion, Usuario usuario) {
        if(nuevaOpcion == null) {
            throw new IllegalArgumentException("La opción de peso no puede ser nula");
        }
        
        Objetivo objetivo = objetivoRepository.findByUsuarioEmail(usuario.getEmail())
            .orElseThrow(() -> new NoSuchElementException("No se encontraron objetivos para el usuario"));
        
        objetivo.setOpcionPeso(nuevaOpcion);
        return objetivoRepository.save(objetivo);
    }
    
    @Override
    public Objetivo actualizarObjetivoUsuario(ObjetivoUsuario nuevoObjetivo, Usuario usuario) {
        if(nuevoObjetivo == null) {
            throw new IllegalArgumentException("El objetivo no puede ser nulo");
        }
        
        Objetivo objetivo = objetivoRepository.findByUsuarioEmail(usuario.getEmail())
            .orElseThrow(() -> new NoSuchElementException("No se encontraron objetivos para el usuario"));
        
        objetivo.setObjetivoUsuario(nuevoObjetivo);
        return objetivoRepository.save(objetivo);
    }


    
}
