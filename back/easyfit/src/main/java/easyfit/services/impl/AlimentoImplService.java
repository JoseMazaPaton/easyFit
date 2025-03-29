package easyfit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import easyfit.models.entities.Alimento;
import easyfit.repositories.IAlimentoRepository;
import easyfit.services.IAlimentoService;

@Service
public class AlimentoImplService extends GenericCrudServiceImpl<Alimento, Integer>  implements IAlimentoService{

	@Autowired
	private IAlimentoRepository alimentoRepository;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected IAlimentoRepository getRepository() {
		return alimentoRepository;
	}
	
	

}
