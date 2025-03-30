package easyfit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import easyfit.models.entities.Progreso;

public interface IProgresoRepository extends JpaRepository<Progreso, Integer>{
	List<Progreso> findByUsuarioEmailOrderByFechaCambioDesc(String email);
}
