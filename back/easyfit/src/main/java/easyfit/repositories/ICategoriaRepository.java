package easyfit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import easyfit.models.dtos.admin.CategoriasRecuento;
import easyfit.models.entities.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {

	  @Query("SELECT new easyfit.models.dtos.admin.CategoriasRecuento(c.idCategoria, c.nombre, COUNT(a)) " +
	           "FROM Categoria c " +
	           "LEFT JOIN Alimento a ON a.categoria.idCategoria = c.idCategoria " +
	           "GROUP BY c.idCategoria, c.nombre")
	    List<CategoriasRecuento> findAllCategoriasConTotalAlimentos();

}


