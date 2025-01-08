package eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eventos.entidades.Reserva;
import eventos.entidades.Usuario;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
	
	List<Reserva> findByUsuario(Usuario usuario);

}
