package pe.edu.upc.matricula.services;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.matricula.models.entities.Curso;

public interface CursoService extends CrudService<Curso, Integer>{
	Optional<Curso>findByCodigo(String codigo) throws Exception;
	List<Curso>findAllByEstado(Boolean estado) throws Exception;
}
