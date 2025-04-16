package easyfit.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Sexo;

public interface IUsuarioRepository extends JpaRepository<Usuario, String>{
	
	List<Usuario> findByEmail(String email);
	List<Usuario> findBySexo(Sexo sexo);
	List<Usuario> findByEdad(int edad); // Busca usuarios por edad exacta

}
