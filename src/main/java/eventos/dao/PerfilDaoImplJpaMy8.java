package eventos.dao;

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

}
