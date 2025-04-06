package easyfit.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import easyfit.models.dtos.objetivo.AjusteSemanalDto;
import easyfit.models.dtos.objetivo.NivelActividadDto;
import easyfit.models.dtos.objetivo.ObjetivoResponseDto;
import easyfit.models.dtos.objetivo.ObjetivoUsuarioDto;
import easyfit.models.dtos.objetivo.PesoActualDto;
import easyfit.models.dtos.objetivo.PesoObjetivoDto;
import easyfit.models.dtos.valornutricional.ValorNutriconalResponseDto;
import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.ObjetivoUsuario;
import easyfit.repositories.IObjetivoRepository;
import easyfit.repositories.IUsuarioRepository;
import easyfit.services.IObjetivoService;
import easyfit.utils.ObjetivoCalculator;

@Service
public class ObjetivoServiceImpl extends GenericCrudServiceImpl<Objetivo, Integer> implements IObjetivoService {

    @Autowired
    private IObjetivoRepository objetivoRepository;
    
    @Autowired
    private IUsuarioRepository usuarioRepository;
    
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

	    //Le añadimos el nuevo peso registrado y a usuario las nuevas kcal racalculadas
	    objetivo.setPesoActual(pesoDto.getPesoActual());
	    usuario.setValorNutricional(ObjetivoCalculator.calcularKcal(usuario, objetivo, usuario.getValorNutricional()));


	    //Actualizamos objetivos con el nuevo peso
	    if (!objetivoRepository.existsById(objetivo.getIdObjetivo())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los objetivos no existen, no se pueden actualizar");
	    } else {
	        updateOne(objetivo); //Usamos el metodo de IGenericCrud para actualizar que ya añade excepciones extra
	    }
	    
	    //Actualizamos usuario con las nuevas kcal
	    if (!usuarioRepository.existsById(usuario.getEmail())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario, no se puede actualizar");
	    } else {
	        usuarioRepository.save(usuario);
	    }

	    //Mapeamos la entidad y le añadimos los valores nutricionales de usuario mapeados ( para mostar que han cambiado al recalcular las kcal)
	    ObjetivoResponseDto respuestaDto = mapper.map(objetivo, ObjetivoResponseDto.class);
	    respuestaDto.setValores(mapper.map(usuario.getValorNutricional(), ValorNutriconalResponseDto.class));
	    respuestaDto.setCoherente(ObjetivoCalculator.esObjetivoCoherente(objetivo));
	    
	    //Devolvemos la respuesta mapeada a dto
	    return respuestaDto ;
	}

	//METODO PARA ACTUALIZAR EL PESO OBJETIVO (TIENE EN CUENTA EL OBJETIVO DEL USUARIO!)
	@Override
	public ObjetivoResponseDto actualizarPesoObjetivo(PesoObjetivoDto pesoDto, Usuario usuario) {

	    // Validaciones iniciales
	    if (pesoDto == null || usuario == null) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El peso o el usuario no pueden ser null");
	    }

	    // Sacamos los objetivos del usuario autenticado
	    Objetivo objetivo = usuario.getObjetivo();
	    if (objetivo == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los objetivos no existen, no se pueden actualizar");
	    }

	    // Metemos el peso objetivo en los objetivos del usuario
	    objetivo.setPesoObjetivo(pesoDto.getPesoObjetivo());

	    // Validamos si el nuevo valor es coherente con el objetivo general (perder, ganar, mantener)
	    if (!ObjetivoCalculator.esObjetivoCoherente(objetivo)) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
	            "El peso objetivo no es coherente con tu objetivo (" + objetivo.getObjetivoUsuario() + ")");
	    }

	    // Actualizamos la entidad en base de datos
	    if (!objetivoRepository.existsById(objetivo.getIdObjetivo())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los objetivos no existen, no se pueden actualizar");
	    } else {
	        updateOne(objetivo); //Usamos el metodo de IGenericCrud para actualizar que ya añade excepciones extra
	    }

	    //Mapeamos al dto de respuesta y le añadimos los valores nutricionales y la coherencia de los datos
	    ObjetivoResponseDto respuestaDto = mapper.map(objetivo, ObjetivoResponseDto.class);
	    respuestaDto.setValores(mapper.map(usuario.getValorNutricional(), ValorNutriconalResponseDto.class));
	    respuestaDto.setCoherente(ObjetivoCalculator.esObjetivoCoherente(objetivo));

	    // Devolvemos la respuesta mapeada
	    return respuestaDto;
	}


	//METODO PARA ACTUALIZAR LA ACTIVIDAD DEL USUARIO + RECALCULAR KCAL
    @Override
    public ObjetivoResponseDto actualizarActividad(NivelActividadDto actividadDto, Usuario usuario) {
    	
    	//Primero comprobamos que el usuario y la actividadDto no sean nulos
        if(usuario == null || actividadDto == null) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La actividad o el usuario no pueden ser nulos");
        }
        
        //Sacamos los objetivos del usuario autenticado y los guardamos 
	    Objetivo objetivo = usuario.getObjetivo();

	    //Comprobamos que no sean null
	    if (objetivo == null) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los objetivos no puede ser null");
	    }

	    //Le añadimos la actividad y a usuario las nuevas kcal recalculadas
	    objetivo.setActividad(actividadDto.getActividad());
	    usuario.setValorNutricional(ObjetivoCalculator.calcularKcal(usuario, objetivo, usuario.getValorNutricional()));

	        
	    //Actualizamos objetivos con la nueva actividad
	    if (!objetivoRepository.existsById(objetivo.getIdObjetivo())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los objetivos no existen, no se pueden actualizar");
	    } else {
	        updateOne(objetivo); //Usamos el metodo de IGenericCrud para actualizar que ya añade excepciones extra
	    }

	    //Actualizamos usuario con las nuevas kcal
	    if (!usuarioRepository.existsById(usuario.getEmail())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario, no se puede actualizar");
	    } else {
	        usuarioRepository.save(usuario);
	    }
	    
	    //Mapeamos al dto de respuesta y le añadimos los valores nutricionales y la coherencia de los datos
	    ObjetivoResponseDto respuestaDto = mapper.map(objetivo, ObjetivoResponseDto.class);
	    respuestaDto.setValores(mapper.map(usuario.getValorNutricional(), ValorNutriconalResponseDto.class));
	    respuestaDto.setCoherente(ObjetivoCalculator.esObjetivoCoherente(objetivo));
	    
	    //Devolvemos la respuesta mapeada a dto
	    return respuestaDto ;
    }
    
    
    
	//METODO PARA ACTUALIZAR LA OPCION PESO SEMANAL DEL USUARIO + RECALCULAR KCAL
    @Override
    public ObjetivoResponseDto actualizarOpcionPeso(AjusteSemanalDto ajusteDto, Usuario usuario) {
    	
    	//Primero comprobamos que el usuario y la actividadDto no sean nulos
        if(usuario == null || ajusteDto == null) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La actividad o el usuario no pueden ser nulos");
        }
        
        //Sacamos los objetivos del usuario autenticado y los guardamos 
	    Objetivo objetivo = usuario.getObjetivo();

	    //Comprobamos que no sean null
	    if (objetivo == null) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los objetivos no puede ser null");
	    }

	    //Le añadimos el nuevo ajuste de peso semanal
	    objetivo.setOpcionPeso(ajusteDto.getOpcionPeso());
	    usuario.setValorNutricional(ObjetivoCalculator.calcularKcal(usuario, objetivo, usuario.getValorNutricional()));

	    
	    //Actualizamos objetivos con el nuevo peso
	    if (!objetivoRepository.existsById(objetivo.getIdObjetivo())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los objetivos no existen, no se pueden actualizar");
	    } else {
	        updateOne(objetivo); //Usamos el metodo de IGenericCrud para actualizar que ya añade excepciones extra
	    }
	    
	    //Actualizamos usuario con las nuevas kcal
	    if (!usuarioRepository.existsById(usuario.getEmail())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario, no se puede actualizar");
	    } else {
	        usuarioRepository.save(usuario);
	    }

	    //Mapeamos al dto de respuesta y le añadimos los valores nutricionales y la coherencia de los datos
	    ObjetivoResponseDto respuestaDto = mapper.map(objetivo, ObjetivoResponseDto.class);
	    respuestaDto.setValores(mapper.map(usuario.getValorNutricional(), ValorNutriconalResponseDto.class));
	    respuestaDto.setCoherente(ObjetivoCalculator.esObjetivoCoherente(objetivo));
	    
	    //Devolvemos la respuesta mapeada a dto
	    return respuestaDto ;
    }
    
    
  //METODO PARA ACTUALIZAR EL TIPO DE OBJETIVO (MANTENER, GANAR O PERDER PESO) Y VALIDAR LA COHERENCIA
    @Override
    public ObjetivoResponseDto actualizarObjetivoUsuario(ObjetivoUsuarioDto nuevoObjetivoDto, Usuario usuario) {

        // Validamos que el dto y el usuario no sean nulos
        if (nuevoObjetivoDto == null || nuevoObjetivoDto.getObjetivoUsuario() == null || usuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El objetivo o el usuario no pueden ser nulos");
        }

        // Sacamos los objetivos del usuario autenticado
        Objetivo objetivo = usuario.getObjetivo();
        if (objetivo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay objetivos asignados al usuario");
        }

        // Extraemos la enum del DTO
        ObjetivoUsuario nuevoObjetivo = nuevoObjetivoDto.getObjetivoUsuario();

        // Guardamos el cambio
        objetivo.setObjetivoUsuario(nuevoObjetivo);

        // Recalculamos kcal con el nuevo objetivo y lo guardamos en usuario
        usuario.setValorNutricional(ObjetivoCalculator.calcularKcal(usuario, objetivo, usuario.getValorNutricional()));

	    //Actualizamos objetivos con el nuevo objetivo
	    if (!objetivoRepository.existsById(objetivo.getIdObjetivo())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los objetivos no existen, no se pueden actualizar");
	    } else {
	        updateOne(objetivo); //Usamos el metodo de IGenericCrud para actualizar que ya añade excepciones extra
	    }
	    
	    //Actualizamos usuario con las nuevas kcal
	    if (!usuarioRepository.existsById(usuario.getEmail())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario, no se puede actualizar");
	    } else {
	        usuarioRepository.save(usuario);
	    }

	    //Mapeamos al dto de respuesta y le añadimos los valores nutricionales y la coherencia de los datos
	    ObjetivoResponseDto respuestaDto = mapper.map(objetivo, ObjetivoResponseDto.class);
	    respuestaDto.setValores(mapper.map(usuario.getValorNutricional(), ValorNutriconalResponseDto.class));
	    respuestaDto.setCoherente(ObjetivoCalculator.esObjetivoCoherente(objetivo));


        // Devolvemos la respuesta mapeada
        return respuestaDto;
    }


    
}
