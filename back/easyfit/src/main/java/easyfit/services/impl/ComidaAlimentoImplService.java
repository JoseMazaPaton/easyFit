package easyfit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import easyfit.models.entities.ComidaAlimento;
import easyfit.repositories.IComidaAlimentoRepository;
import easyfit.services.IComidaAlimentoService;

@Service
public class ComidaAlimentoImplService extends GenericCrudServiceImpl<ComidaAlimento, Integer> implements IComidaAlimentoService{

	@Autowired
	private IComidaAlimentoRepository cAlimentoRepository;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected IComidaAlimentoRepository getRepository() {
		return cAlimentoRepository;
	}
	

}
