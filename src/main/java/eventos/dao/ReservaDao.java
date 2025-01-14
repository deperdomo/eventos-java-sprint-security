package eventos.dao;

import java.util.List;

import eventos.entidades.Evento;
import eventos.entidades.Reserva;
import eventos.entidades.Usuario;

public interface ReservaDao {

	List<Reserva> buscarReservasPorUsuario(Usuario usuario);
	List<Reserva> buscarReservasPorEvento(Evento evento);
	int guardarReserva(Reserva reserva);
	int eliminarReserva(Reserva reserva);
	Reserva buscarPorIdReserva(int idReserva);
	
}
