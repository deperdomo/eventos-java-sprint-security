package eventos.dao;

import java.util.List;

import eventos.entidades.Usuario;

public interface UsuarioDao {
	
	Usuario buscarPorUsernameYPassword(String username, String password);
	Usuario buscarPorUsername(String username);
	int insertarUsuario(Usuario usuario);
	List<Usuario> buscarTodos();
	int eliminarUsuario(String username);
	
}
