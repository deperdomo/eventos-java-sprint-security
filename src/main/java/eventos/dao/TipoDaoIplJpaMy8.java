package eventos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.entidades.Tipo;
import eventos.repository.TipoRepository;

@Repository
public class TipoDaoIplJpaMy8 implements TipoDao{

	@Autowired
	TipoRepository trepo;
	
	@Override
	public List<Tipo> buscarTodos() {
		return trepo.findAll();
	}

	@Override
	public Tipo buscarPorId(int idTipo) {
		return trepo.findByIdTipo(idTipo);
	}

	@Override
	public int eliminarTipo(Tipo tipo) {
		if (trepo.existsById(tipo.getIdTipo())) {
			trepo.delete(tipo);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int insertarTipo(Tipo tipo) {
		return (trepo.save(tipo) != null) ? 1 : 0;
	}

}
