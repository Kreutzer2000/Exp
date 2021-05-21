package pe.edu.upc.matricula.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.matricula.models.entities.DocenteCurso;

@Repository
public interface DocenteCursoRepository extends JpaRepository<DocenteCurso, Integer>{
	List<DocenteCurso>findByDocente(Integer docente) throws Exception;
	
	List<DocenteCurso>findByCurso(Integer curso) throws Exception;
}
