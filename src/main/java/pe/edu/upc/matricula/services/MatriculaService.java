package pe.edu.upc.matricula.services;

import java.util.Optional;

import pe.edu.upc.matricula.models.entities.Alumno;
import pe.edu.upc.matricula.models.entities.Matricula;

public interface MatriculaService extends CrudService<Matricula, Integer>{

	Optional<Matricula> findByAlumno(Alumno alumno) throws Exception;
}
