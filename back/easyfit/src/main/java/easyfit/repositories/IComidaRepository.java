package easyfit.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import easyfit.models.entities.Comida;

public interface IComidaRepository extends JpaRepository<Comida, Integer>{
	List<Comida> findByFechaAndUsuarioEmail(LocalDate fecha, String email);
}
