package easyfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import easyfit.models.entities.Comida;

public interface IComidaRepository extends JpaRepository<Comida, Integer>{

}
