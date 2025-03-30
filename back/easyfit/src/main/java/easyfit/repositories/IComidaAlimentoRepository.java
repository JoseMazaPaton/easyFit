package easyfit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import easyfit.models.entities.ComidaAlimento;

public interface  IComidaAlimentoRepository extends JpaRepository<ComidaAlimento, Integer> {
	
	List<ComidaAlimento> findByComidaIdComida(int idComida);
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM ComidaAlimento ca WHERE ca.comida.idComida = :idComida AND ca.alimento.idAlimento = :idAlimento")
    void deleteByComidaAndAlimento(int idComida, int idAlimento);
	
	@Query("SELECT ca FROM ComidaAlimento ca WHERE ca.comida.idComida = :idComida AND ca.alimento.idAlimento = :idAlimento")
	Optional<ComidaAlimento> findByComidaIdComidaAndAlimentoIdAlimento(int idComida, int idAlimento);
	
}
