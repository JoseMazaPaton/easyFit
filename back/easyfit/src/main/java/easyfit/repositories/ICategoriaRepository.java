package easyfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import easyfit.models.entities.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {

}
