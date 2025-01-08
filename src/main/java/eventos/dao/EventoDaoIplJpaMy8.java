package eventos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.entidades.Evento;
import eventos.entidades.Tipo;
import eventos.repository.EventoRepository;

@Repository
public class EventoDaoIplJpaMy8 implements EventoDao {

	@Autowired
	EventoRepository erepo;
	
	@Override
	public List<Evento> buscarTodos() {
		return erepo.findAll();
	}

	@Override
	public List<Evento> buscarPorEstado(String estado) {
		return erepo.findByEstado(estado);
	}

	@Override
	public List<Evento> buscarPorDestacados(String destacado) {
		return erepo.findByDestacado(destacado);
	}

	@Override
	public Evento buscarPorId(int idEvento) {
		return erepo.findByIdEvento(idEvento);
	}

	@Override
	public int agregarEvento(Evento evento) {
		return (erepo.save(evento) == null) ? 0 : 1;
	}

	@Override
	public int eliminarEvento(Evento evento) {
		if (erepo.existsById(evento.getIdEvento())) {
			erepo.delete(evento);
			return 1;
		} else {
			return 0;
		}
			
	}

	@Override
	public List<Evento> buscarPorTipo(Tipo tipo) {
		return erepo.findByTipo(tipo);
	}

	

	

}
