package easyfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import easyfit.models.entities.ComidaAlimento;

public interface IComidaAlimentoRepository extends JpaRepository<ComidaAlimento, Integer> {

}
