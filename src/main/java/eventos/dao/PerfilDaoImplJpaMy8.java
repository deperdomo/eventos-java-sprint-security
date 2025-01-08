package eventos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.entidades.Perfil;
import eventos.repository.PerfilRepository;

@Repository
public class PerfilDaoImplJpaMy8 implements PerfilDao {

	@Autowired
	PerfilRepository prepo;
	
	@Override
	public Perfil buscarPorNombreDePerfil(String nombre) {
		return prepo.findByNombre(nombre);
	}

	@Override
	public List<Perfil> buscarTodos() {
		return prepo.findAll();
	}

	@Override
	public int insertarPerfil(Perfil perfil) {
		return (prepo.save(perfil) != null) ? 1 : 0;
	}

	@Override
	public Perfil buscarPorId(int idPerfil) {
		return prepo.findByIdPerfil(idPerfil);
	}

}
