package eventos.dao;

import java.util.List;

import eventos.entidades.Reserva;
import eventos.entidades.Usuario;

public interface ReservaDao {

	List<Reserva> buscarReservasPorUsuario(Usuario usuario);
	int guardarReserva(Reserva reserva);
	int eliminarReserva(Reserva reserva);
	Reserva buscarPorIdReserva(int idReserva);
	
}
