package easyfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import easyfit.models.entities.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, String> {

}
