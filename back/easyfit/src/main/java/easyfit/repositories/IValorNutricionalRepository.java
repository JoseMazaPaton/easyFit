package easyfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import easyfit.models.entities.ValorNutricional;

public interface IValorNutricionalRepository extends JpaRepository<ValorNutricional, Integer> {

}
