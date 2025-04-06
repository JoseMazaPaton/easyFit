package easyfit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import easyfit.models.entities.Progreso;

public interface IProgresoRepository extends JpaRepository<Progreso, Integer>{

	Optional<Progreso> findFirstByUsuario_EmailOrderByFechaCambioAsc(String email);
;

}