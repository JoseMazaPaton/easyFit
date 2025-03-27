package easyfit.services;

import java.util.List;

public interface IGenericCrud <E,ID> {
	
	List<E> findAll ();
	E findById (ID id);
	E updateOne (E entity);
	E insertOne (E entity);
	void deleteOne (ID id);
}
