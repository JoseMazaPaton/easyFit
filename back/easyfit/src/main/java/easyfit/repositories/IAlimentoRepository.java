package easyfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import easyfit.models.entities.Alimento;

public interface IAlimentoRepository extends JpaRepository<Alimento, Integer>{

}
