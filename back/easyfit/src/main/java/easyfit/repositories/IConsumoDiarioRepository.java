package easyfit.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import easyfit.models.entities.ConsumoDiario;

public interface IConsumoDiarioRepository extends JpaRepository<ConsumoDiario, Integer>{
	
	 Optional<ConsumoDiario> findByFechaAndUsuarioEmail(LocalDate fecha, String email);
	
	List<ConsumoDiario> findByUsuarioEmailAndFechaBetweenOrderByFechaAsc(String email, LocalDate desde, LocalDate hasta);

	 
}
