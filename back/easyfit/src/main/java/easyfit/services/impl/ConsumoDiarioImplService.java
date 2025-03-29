package easyfit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import easyfit.models.entities.ConsumoDiario;
import easyfit.repositories.IConsumoDiarioRepository;
import easyfit.services.IConsumoDiarioService;

@Service
public class ConsumoDiarioImplService extends GenericCrudServiceImpl<ConsumoDiario,Integer> implements IConsumoDiarioService{
	
	@Autowired
	private IConsumoDiarioRepository cDiarioRepository;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected  IConsumoDiarioRepository getRepository() {
		return cDiarioRepository;
	}


}
