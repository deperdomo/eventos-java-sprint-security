package eventos.dao;

import java.util.List;

import eventos.entidades.Evento;
import eventos.entidades.Tipo;

public interface EventoDao {
	
	List<Evento> buscarTodos();
	List<Evento> buscarPorEstado(String estado);
	List<Evento> buscarPorDestacados(String destacado);
	Evento buscarPorId(int idEvento);
	int agregarEvento(Evento evento);
	int eliminarEvento(Evento evento);
	List<Evento> buscarPorTipo(Tipo tipo);
	int aforoRestante(int idEvento);
}
