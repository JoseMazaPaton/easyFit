package easyfit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import easyfit.models.entities.Comida;
import easyfit.repositories.IComidaRepository;
import easyfit.services.IComidaService;

@Service
public class ComidaImplService extends GenericCrudServiceImpl<Comida, Integer> implements IComidaService{

	@Autowired
	private IComidaRepository comidaRepository;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected  IComidaRepository getRepository() {
		return comidaRepository;
	}
	

}
