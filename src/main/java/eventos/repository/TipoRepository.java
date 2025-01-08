package eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventos.entidades.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Integer> {
	Tipo findByIdTipo(int idTipo);
}
