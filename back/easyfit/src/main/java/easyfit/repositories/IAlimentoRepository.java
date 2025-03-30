package easyfit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import easyfit.models.entities.Alimento;

public interface IAlimentoRepository extends JpaRepository<Alimento, Integer>{

	
	public List<Alimento> findByNombreContainingIgnoreCase(String nombre);
	List<Alimento> findByCreadoPor_Email(String email);
}
