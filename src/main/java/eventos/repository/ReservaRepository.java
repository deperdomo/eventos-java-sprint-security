package eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eventos.entidades.Evento;
import eventos.entidades.Reserva;
import eventos.entidades.Usuario;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
	
	List<Reserva> findByUsuario(Usuario usuario);
	List<Reserva> findByEvento(Evento evento);
	Reserva findByIdReserva(int idReserva);

}
