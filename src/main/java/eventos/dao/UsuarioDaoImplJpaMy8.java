package eventos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.entidades.Usuario;
import eventos.repository.UsuarioRepository;

@Repository
public class UsuarioDaoImplJpaMy8 implements UsuarioDao {

	@Autowired
	UsuarioRepository urepo;
	
	@Override
	public Usuario buscarPorUsernameYPassword(String username, String password) {
		return urepo.findByUsernameAndPassword(username, password);
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		return urepo.findByUsername(username);
	}

	@Override
	public int insertarUsuario(Usuario usuario) {
		return (urepo.save(usuario)!= null) ? 1 : 0;
	}

	@Override
	public List<Usuario> buscarTodos() {
		return urepo.findAll();
	}

	@Override
	public int eliminarUsuario(String username) {
		if (urepo.existsById(username)) {
			urepo.delete(urepo.findByUsername(username));
			return 1;
		} else {
			return 0;
		}	
	}

	
	
	
}
