package easyfit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import easyfit.models.entities.Categoria;
import easyfit.repositories.ICategoriaRepository;
import easyfit.services.ICategoriaService;
@Service
public class CategoriaImplService extends GenericCrudServiceImpl<Categoria, Integer> implements ICategoriaService{
	
	@Autowired
	private ICategoriaRepository categoriaRepository;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected ICategoriaRepository getRepository() {
		return categoriaRepository;
	}


	

}
