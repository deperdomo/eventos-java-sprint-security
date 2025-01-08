package eventos.dao;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.entidades.Reserva;
import eventos.entidades.Usuario;
import eventos.repository.ReservaRepository;

@Repository
public class ReservaDaoIplJpaMy8 implements ReservaDao {

	@Autowired
	ReservaRepository rrepo;
	
	@Override
	public List<Reserva> buscarReservasPorUsuario(Usuario usuario) {
		return rrepo.findByUsuario(usuario);
	}

	@Override
	public int guardarReserva(Reserva reserva) {
		return (rrepo.save(reserva) == null) ? 1 : 0;
	}

	@Override
	public int eliminarReserva(Reserva reserva) {
		rrepo.delete(reserva);
		return 1;
	}

	@Override
	public Reserva buscarPorIdReserva(int idReserva) {
		return rrepo.findByIdReserva(idReserva);
	}

}
