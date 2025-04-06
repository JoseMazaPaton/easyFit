package easyfit.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import easyfit.models.entities.Objetivo;

public interface IObjetivoRepository extends JpaRepository<Objetivo, Integer>{
	
	Optional<Objetivo> findByUsuarioEmail(String email);

}
