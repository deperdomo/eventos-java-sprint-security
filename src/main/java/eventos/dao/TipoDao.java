package eventos.dao;

import java.util.List;

import eventos.entidades.Tipo;

public interface TipoDao {
	
	List<Tipo> buscarTodos();
	Tipo buscarPorId(int idTipo);
	int eliminarTipo(Tipo tipo);
	int insertarTipo(Tipo tipo);
}
