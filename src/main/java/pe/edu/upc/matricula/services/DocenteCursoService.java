package pe.edu.upc.matricula.services;

import java.util.List;

import pe.edu.upc.matricula.models.entities.DocenteCurso;

public interface DocenteCursoService extends CrudService<DocenteCurso, Integer>{

	List<DocenteCurso>findByDocente(Integer docente) throws Exception;
	
	List<DocenteCurso>findByCurso(Integer curso) throws Exception;
}
