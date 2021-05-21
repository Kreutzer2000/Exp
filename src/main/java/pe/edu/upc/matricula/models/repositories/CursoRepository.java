package pe.edu.upc.matricula.models.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.matricula.models.entities.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

	Optional<Curso>findByCodigo(String codigo) throws Exception;
	List<Curso>findAllByEstado(Boolean estado) throws Exception;
}
