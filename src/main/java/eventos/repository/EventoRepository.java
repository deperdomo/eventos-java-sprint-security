package eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eventos.entidades.Evento;
import eventos.entidades.Tipo;

public interface EventoRepository extends JpaRepository<Evento, Integer>  {
	
	List<Evento> findByEstado(String estado);
	List<Evento> findByDestacado(String destacado);
	List<Evento> findByTipo(Tipo tipo);
	Evento findByIdEvento(int idEvento);
}
