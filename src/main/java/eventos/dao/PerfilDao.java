package eventos.dao;

import java.util.List;

import eventos.entidades.Perfil;

public interface PerfilDao {
	Perfil buscarPorNombreDePerfil(String nombre);
	List<Perfil> buscarTodos();
	int insertarPerfil(Perfil perfil);
	Perfil buscarPorId(int idPerfil);
	
}
