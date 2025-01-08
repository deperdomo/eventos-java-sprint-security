package eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventos.entidades.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	
	Usuario findByUsernameAndPassword(String username, String password);
	Usuario findByUsername(String username);
}
