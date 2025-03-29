package easyfit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import easyfit.models.entities.Progreso;
import easyfit.repositories.IProgresoRepository;
import easyfit.services.IProgresoService;

@Service
public class ProgresoImplRepository extends GenericCrudServiceImpl<Progreso,Integer> implements IProgresoService{
	
	@Autowired
	private IProgresoRepository progresoRepository;
	
	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected IProgresoRepository getRepository() {
		return progresoRepository;
	}



}
